using Microsoft.AspNetCore.Mvc;

namespace WebApi.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ExemploController : ControllerBase
    {
        [HttpGet("get1")]
        public IActionResult Get1()
        {
            return Ok("Mensagem de exemplo do GET1!");
        }

        [HttpGet("get2")]
        public IActionResult Get2()
        {
            return Ok("Mensagem de exemplo do GET2!");
        }

        [HttpPost("post1")]
        public IActionResult Post1([FromBody] string mensagem)
        {
            return Ok($"Mensagem recebida1: {mensagem}");
        }

        [HttpPost("post2")]
        public IActionResult Post2([FromBody] string mensagem)
        {
            return Ok($"Mensagem recebida2: {mensagem}");
        }
    }
}
