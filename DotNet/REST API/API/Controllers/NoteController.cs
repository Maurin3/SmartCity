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
    [Route("api/Note")]
    public class NoteController : BaseController
    {
        private readonly namikotDBContext _context;

        public NoteController(namikotDBContext context, UserManager<ApplicationUser> uMgr) : base(uMgr)
        {
            _context = context;
        }

        // GET: api/Note
        [HttpGet]
        public IEnumerable<Note> GetNote()
        {
            return _context.Note;
        }

        // GET: api/Note/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetNote([FromRoute] decimal id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var note = await _context.Note.SingleOrDefaultAsync(m => m.Id == id);

            if (note == null)
            {
                return NotFound();
            }

            return Ok(note);
        }

        // PUT: api/Note/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutNote([FromRoute] decimal id, [FromBody] Note note)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != note.Id)
            {
                return BadRequest();
            }

            _context.Entry(note).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!NoteExists(id))
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

        // POST: api/Note
        [HttpPost]
        public async Task<IActionResult> PostNote([FromBody] Note note)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.Note.Add(note);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetNote", new { id = note.Id }, note);
        }

        // DELETE: api/Note/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteNote([FromRoute] decimal id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var note = await _context.Note.SingleOrDefaultAsync(m => m.Id == id);
            if (note == null)
            {
                return NotFound();
            }

            _context.Note.Remove(note);
            await _context.SaveChangesAsync();

            return Ok(note);
        }

        private bool NoteExists(decimal id)
        {
            return _context.Note.Any(e => e.Id == id);
        }
    }
}