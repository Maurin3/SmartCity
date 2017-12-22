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
    [Route("api/UserInfo")]
    public class UserInfoController : BaseController
    {
        private readonly namikotDBContext _context;

        public UserInfoController(namikotDBContext context, UserManager<ApplicationUser> uMgr) : base(uMgr)
        {
            _context = context;
        }

        // GET: api/UserInfo
        [HttpGet]
        public IEnumerable<UserInfo> GetUserInfo()
        {
            return _context.UserInfo
                .Include(user => user.IdAspNetUserNavigation);
        }

        // GET: api/UserInfo/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetUserInfo([FromRoute] string id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var userInfo = await _context.UserInfo.Include(user => user.IdAspNetUserNavigation)
                            .SingleOrDefaultAsync(m => m.IdAspNetUserNavigation.Id == id);

            if (userInfo == null)
            {
                return NotFound();
            }

            return Ok(userInfo);
        }

        // PUT: api/UserInfo/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutUserInfo([FromRoute] string id, [FromBody] UserInfo userInfo)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != userInfo.IdAspNetUserNavigation.Id)
            {
                return BadRequest();
            }

            var user = await _context.UserInfo.Include(u => u.IdAspNetUserNavigation)
                           .SingleOrDefaultAsync(m => m.IdAspNetUserNavigation.Id == id);

            user.Birthdate = userInfo.Birthdate;
            user.FirstName = userInfo.FirstName;
            user.LastName = userInfo.LastName;

            _context.Entry(user).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
                return Ok();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UserInfoExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }
        }

        // POST: api/UserInfo
        [HttpPost]
        public async Task<IActionResult> PostUserInfo([FromBody] UserInfo userInfo)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            var user = await _context.AspNetUsers.FindAsync(userInfo.IdAspNetUser);
            userInfo.IdAspNetUserNavigation = user;
            _context.UserInfo.Add(userInfo);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetUserInfo", new { id = userInfo.Id }, userInfo);
        }

        // DELETE: api/UserInfo/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteUserInfo([FromRoute] decimal id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var userInfo = await _context.UserInfo.SingleOrDefaultAsync(m => m.Id == id);
            if (userInfo == null)
            {
                return NotFound();
            }

            _context.UserInfo.Remove(userInfo);
            await _context.SaveChangesAsync();

            return Ok(userInfo);
        }

        private bool UserInfoExists(string id)
        {
            return _context.UserInfo.Any(e => e.IdAspNetUserNavigation.Id == id);
        }
    }
}