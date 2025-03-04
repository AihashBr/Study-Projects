using Application.DTOs.Product;
using Application.Interface;
using Microsoft.AspNetCore.Mvc;

namespace Api.Controllers;
[Route("api/[controller]")]
[ApiController]
public class ProductsController : ControllerBase
{
    private readonly IProductService _productService;

    public ProductsController(IProductService productService)
    {
        _productService = productService;
    }

    [HttpGet("{id}")]
    public IActionResult GetProductById(int id)
    {
        return Ok(_productService.GetProductById(id));
    }

    [HttpPost]
    public IActionResult CreateProduct([FromBody] ProductCreateDTO productDTO)
    {
        return Ok(_productService.CreateProduct(productDTO));
    }

    [HttpPut]
    public IActionResult UpdateProduct([FromBody] ProductCreateDTO productDTO)
    {
        return Ok(_productService.UpdateProduct(productDTO));
    }

    [HttpDelete("{id}")]
    public IActionResult DeleteProduct(int id)
    {
        return Ok(_productService.DeleteProduct(id));
    }
}
