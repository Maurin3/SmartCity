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
    [Route("api/PicturePath")]
    public class PicturePathController : BaseController
    {
        private readonly namikotDBContext _context;

        public PicturePathController(namikotDBContext context, UserManager<ApplicationUser> userManager)
            : base(userManager)
        {
            _context = context;
        }

        // GET: api/PicturePath
        [HttpGet]
        public IEnumerable<PicturePath> GetPicturePath()
        {
            return _context.PicturePath;
        }

        // GET: api/PicturePath/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetPicturePath([FromRoute] decimal id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var picturePath = await _context.PicturePath.SingleOrDefaultAsync(m => m.Id == id);

            if (picturePath == null)
            {
                return NotFound();
            }

            return Ok(picturePath);
        }

        // PUT: api/PicturePath/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutPicturePath([FromRoute] decimal id, [FromBody] PicturePath picturePath)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != picturePath.Id)
            {
                return BadRequest();
            }

            _context.Entry(picturePath).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!PicturePathExists(id))
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

        // POST: api/PicturePath
        [HttpPost]
        public async Task<IActionResult> PostPicturePath([FromBody] PicturePath picturePath)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.PicturePath.Add(picturePath);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetPicturePath", new { id = picturePath.Id }, picturePath);
        }

        // DELETE: api/PicturePath/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeletePicturePath([FromRoute] decimal id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var picturePath = await _context.PicturePath.SingleOrDefaultAsync(m => m.Id == id);
            if (picturePath == null)
            {
                return NotFound();
            }

            _context.PicturePath.Remove(picturePath);
            await _context.SaveChangesAsync();

            return Ok(picturePath);
        }

        private bool PicturePathExists(decimal id)
        {
            return _context.PicturePath.Any(e => e.Id == id);
        }
    }
}