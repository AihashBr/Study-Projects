using Application.DTOs.Product;
using Application.Interface;
using Domain.Model;
using Infrastructure.Repository.Interface;

namespace Application.Services;
public class ProductService : IProductService
{
    private readonly IProductRepository _productRepository;

    public ProductService(IProductRepository productRepository)
    {
        _productRepository = productRepository;
    }

    public string CreateProduct(ProductDTO productDTO)
    {
        var product = new Product
        {
            Name = productDTO.Name,
            Amount = productDTO.Amount,
            Price = productDTO.Price
        };

        _productRepository.AddProductAsync(product).Wait();

        return $"Produto criado com sucesso!";
    }

    public string GetProductById(int id)
    {
        var product = _productRepository.GetProductByIdAsync(id).Result;

        if (product != null)
        {
            return $"Product com ID {id} encontrado: {product.Name}, Preço: {product.Amount}, Quantidade: {product.Price}";
        }

        return $"Product com ID {id} não encontrado.";
    }

    public string UpdateProduct(ProductDTO productDTO)
    {
        var product = new Product
        {
            Id = productDTO.Id,
            Name = productDTO.Name,
            Amount = productDTO.Amount,
            Price = productDTO.Price
        };

        _productRepository.UpdateProductAsync(product).Wait();

        return $"Produto com ID {productDTO.Id} atualizado com sucesso!";
    }

    public string DeleteProduct(int id)
    {
        _productRepository.DeleteProductAsync(id).Wait();

        return $"Produto com ID {id} deletado com sucesso!";
    }
}
