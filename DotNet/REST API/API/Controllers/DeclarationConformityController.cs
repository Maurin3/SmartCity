using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Model;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Authentication.JwtBearer;

namespace API.Controllers
{
    [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
    [Route("api/DeclarationConformity")]
    public class DeclarationConformityController : BaseController
    {
        private readonly namikotDBContext _context;

        public DeclarationConformityController(namikotDBContext context, UserManager<ApplicationUser> userManager)
            : base(userManager)
        {
            _context = context;
        }

        // GET: api/DeclarationConformity
        [HttpGet]
        public IEnumerable<DeclarationConformity> GetDeclarationConformity()
        {
            return _context.DeclarationConformity;
        }

        // GET: api/DeclarationConformity/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetDeclarationConformity([FromRoute] decimal id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var declarationConformity = await _context.DeclarationConformity.SingleOrDefaultAsync(m => m.Id == id);

            if (declarationConformity == null)
            {
                return NotFound();
            }

            return Ok(declarationConformity);
        }

        // PUT: api/DeclarationConformity/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutDeclarationConformity([FromRoute] decimal id, [FromBody] DeclarationConformity declarationConformity)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != declarationConformity.Id)
            {
                return BadRequest();
            }

            _context.Entry(declarationConformity).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!DeclarationConformityExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // POST: api/DeclarationConformity
        [HttpPost]
        public async Task<IActionResult> PostDeclarationConformity([FromBody] DeclarationConformity declarationConformity)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.DeclarationConformity.Add(declarationConformity);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetDeclarationConformity", new { id = declarationConformity.Id }, declarationConformity);
        }

        // DELETE: api/DeclarationConformity/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteDeclarationConformity([FromRoute] decimal id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var declarationConformity = await _context.DeclarationConformity.SingleOrDefaultAsync(m => m.Id == id);
            if (declarationConformity == null)
            {
                return NotFound();
            }

            _context.DeclarationConformity.Remove(declarationConformity);
            await _context.SaveChangesAsync();

            return Ok(declarationConformity);
        }

        private bool DeclarationConformityExists(decimal id)
        {
            return _context.DeclarationConformity.Any(e => e.Id == id);
        }
    }
}