using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using API.DTO;
using Model;

namespace API.Controllers
{
    [Route("api/[controller]")]
    public class AccountController : Controller
    {
        private UserManager<ApplicationUser> _userManager;
        private SignInManager<ApplicationUser> _signInManager;
        public AccountController(UserManager<ApplicationUser> userManager, SignInManager<ApplicationUser> signInManager)
        {
            this._userManager = userManager;
            this._signInManager = signInManager;
        }

        [HttpPost]
        public async Task<IActionResult> Post([FromBody]NewUserDTO dto)
        {
            if (ModelState.IsValid) {
                var newUser = new ApplicationUser {
                    UserName = dto.UserName,
                    Email = dto.Email

                };
                IdentityResult result = await _userManager.CreateAsync(newUser, dto.Password);
                return (result.Succeeded)?Created("Account created in database", newUser):(IActionResult)BadRequest();
            }
            return (IActionResult)BadRequest();
        }

        [HttpGet("{userName}")]
        public async Task<IActionResult> Get([FromRoute] string userName)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }


            var user = await _userManager.FindByNameAsync(userName);

                if(user == null)
                {
                    return NotFound();
                }

            return Ok(user);
        }

        [HttpPut("{userName}")]
        public async Task<IActionResult> Put([FromRoute] string userName, [FromBody] NewUserDTO dto)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var user = await _userManager.FindByNameAsync(userName);
            if(user != null)
            {
                var token = await _userManager.GeneratePasswordResetTokenAsync(user);
                if(dto.Email == user.Email)
                {
                    IdentityResult result = await _userManager.ResetPasswordAsync(user, token, dto.Password);
                    return (result.Succeeded)? Ok() : (IActionResult)BadRequest();
                }
                return (IActionResult)BadRequest();
            }
            else
            {
                return NotFound();
            }
        }
    }
}