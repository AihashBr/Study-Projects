using Microsoft.AspNetCore.Mvc;

namespace Backend.Api.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ExampleController : ControllerBase
    {
        [HttpGet]
        public IActionResult Get()
        {
            return Ok(new { message = "Hello from Swagger!" });
        }

        [HttpPost]
        public IActionResult Post([FromBody] string value)
        {
            return Ok(new { message = $"You posted: {value}" });
        }
    }
}
