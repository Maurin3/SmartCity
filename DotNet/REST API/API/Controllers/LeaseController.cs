using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Model;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Identity;

namespace API.Controllers
{
    [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
    [Route("api/Lease")]
    public class LeaseController : BaseController
    {
        private readonly namikotDBContext _context;

        public LeaseController(namikotDBContext context, UserManager<ApplicationUser> userManager)
            : base(userManager)
        {
            _context = context;
        }

        // GET: api/Lease
        [HttpGet]
        public IEnumerable<Lease> GetLease()
        {
            return _context.Lease;
        }

        // GET: api/Lease/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetLease([FromRoute] decimal id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var lease = await _context.Lease.SingleOrDefaultAsync(m => m.Id == id);

            if (lease == null)
            {
                return NotFound();
            }

            return Ok(lease);
        }

        // PUT: api/Lease/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutLease([FromRoute] decimal id, [FromBody] Lease lease)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != lease.Id)
            {
                return BadRequest();
            }

            _context.Entry(lease).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!LeaseExists(id))
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

        // POST: api/Lease
        [HttpPost]
        public async Task<IActionResult> PostLease([FromBody] Lease lease)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.Lease.Add(lease);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetLease", new { id = lease.Id }, lease);
        }

        // DELETE: api/Lease/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteLease([FromRoute] decimal id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var lease = await _context.Lease.SingleOrDefaultAsync(m => m.Id == id);
            if (lease == null)
            {
                return NotFound();
            }

            _context.Lease.Remove(lease);
            await _context.SaveChangesAsync();

            return Ok(lease);
        }

        private bool LeaseExists(decimal id)
        {
            return _context.Lease.Any(e => e.Id == id);
        }
    }
}