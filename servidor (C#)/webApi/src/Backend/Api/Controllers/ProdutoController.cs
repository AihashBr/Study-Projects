using BackEnd.Application.DTOs;
using BackEnd.Application.Services.Produto;
using Microsoft.AspNetCore.Mvc;

namespace WebApi.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ProdutoController : ControllerBase
    {
        private readonly IProdutoService _produtoService;

        public ProdutoController(IProdutoService produtoService)
        {
            _produtoService = produtoService;
        }

        // Endpoint para criar um produto
        [HttpPost]
        public IActionResult CreateProduto([FromBody] ProdutoDTO produtoDTO)
        {
            return Ok(_produtoService.CreateProduto(produtoDTO));
        }

        // Endpoint para buscar produto por ID
        [HttpGet("{id}")]
        public IActionResult GetProdutoById(int id)
        {
            return Ok(_produtoService.GetProdutoById(id));
        }
    }
}