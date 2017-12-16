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
    [Route("api/Building")]
    public class BuildingController : BaseController
    {
        private readonly namikotDBContext _context;

        public BuildingController(namikotDBContext context, UserManager<ApplicationUser> uMgr) : base (uMgr)
        {
            _context = context;
        }

        // GET: api/Building
        [HttpGet]
        public IEnumerable<Building> GetBuilding()
        {
            return _context.Building;
        }

        // GET: api/Building/5
        [HttpGet("{id}")] //Au lieu de prendre l'id, on change et on peut prendre le login
        public async Task<IActionResult> GetBuilding([FromRoute] string id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var building = await _context.Building.SingleOrDefaultAsync(m => m.Street == id);

            if (building == null)
            {
                return NotFound();
            }

            return Ok(building);
        }

        // PUT: api/Building/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutBuilding([FromRoute] string id, [FromBody] Building building)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != building.Street)
            {
                return BadRequest();
            }

            _context.Entry(building).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!BuildingExists(id))
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

        // POST: api/Building
        [HttpPost]
        public async Task<IActionResult> PostBuilding([FromBody] Building building)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.Building.Add(building);
            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateException)
            {
                if (BuildingExists(building.Street))
                {
                    return new StatusCodeResult(StatusCodes.Status409Conflict);
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtAction("GetBuilding", new { id = building.Street }, building);
        }

        // DELETE: api/Building/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteBuilding([FromRoute] string id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var building = await _context.Building.SingleOrDefaultAsync(m => m.Street == id);
            if (building == null)
            {
                return NotFound();
            }

            _context.Building.Remove(building);
            await _context.SaveChangesAsync();

            return Ok(building);
        }

        private bool BuildingExists(string id)
        {
            return _context.Building.Any(e => e.Street == id);
        }
    }
}