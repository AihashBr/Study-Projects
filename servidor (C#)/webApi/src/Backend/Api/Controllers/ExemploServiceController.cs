using BackEnd.Application.Services;
using Microsoft.AspNetCore.Mvc;

namespace WebApi.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ExemploServiceController : ControllerBase
    {
        private readonly IExemploService _exemploService;

        public ExemploServiceController(IExemploService exemploService)
        {
            _exemploService = exemploService;
        }

        [HttpGet]
        public IActionResult GetExemplo()
        {
            var message = _exemploService.GetMessage();
            return Ok(message);
        }

        [HttpPost]
        public IActionResult PostExemplo([FromBody] string mensagem)
        {
            var response = _exemploService.PostMessage(mensagem);
            return Ok(response);
        }
    }
}
