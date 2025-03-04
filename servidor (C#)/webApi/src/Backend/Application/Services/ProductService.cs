using Application.DTOs.Product;
using Application.DTOs.Return;
using Application.Interface;
using AutoMapper;
using Domain.Model;
using Infrastructure.Data;
using Infrastructure.Repository.Interface;
using Microsoft.EntityFrameworkCore;

namespace Application.Services;
public class ProductService : IProductService
{
    private readonly IProductRepository _productRepository;
    private readonly IMapper _mapper;
    private readonly ApplicationDbContext _context;

    public ProductService(IProductRepository productRepository, IMapper mapper, ApplicationDbContext context)
    {
        _productRepository = productRepository;
        _mapper = mapper;
        _context = context;
    }

    public ReturnStatus CreateProduct(ProductCreateDTO newProduct)
    {

        var product = _mapper.Map<Product>(newProduct);

        _productRepository.AddProductAsync(product).Wait();

        return new ReturnStatus { Status = true, Message = $"Produto criado com sucesso!"};
    }

    public ProductViewDTO GetProductById(int id)
    {
        var product = _productRepository.GetProductByIdAsync(id).Result;

        var productViewDTO = _mapper.Map<ProductViewDTO>(product);

        if (product != null)
        {
            productViewDTO.Status = true;
            productViewDTO.Message = $"Produto com ID {id} encontrado com sucesso!";

            return productViewDTO;
        }

        return new ProductViewDTO { Status = false, Message = $"Produto com ID {id} não encontrado!" };
    }

    public ReturnStatus UpdateProduct(ProductCreateDTO newProduct)
    {
        var productGetById = _productRepository.GetProductByIdAsync(newProduct.Id).Result;
        if (productGetById == null)
        {
            return new ReturnStatus { Status = false, Message = $"Produto com ID {newProduct.Id} não encontrado!" };
        }

        var product = _mapper.Map<Product>(newProduct);

        _context.Entry(productGetById).State = EntityState.Detached;

        _productRepository.UpdateProductAsync(product).Wait();

        return new ReturnStatus { Status = true, Message = $"Produto atualizado com sucesso!" };
    }

    public ReturnStatus DeleteProduct(int id)
    {
        var productGetById = _productRepository.GetProductByIdAsync(id).Result;
        if (productGetById == null)
        {
            return new ReturnStatus { Status = false, Message = $"Produto com ID {id} não encontrado!" };
        }

        _productRepository.DeleteProductAsync(id).Wait();

        return new ReturnStatus { Status = true, Message = $"Produto com ID {id} deletado com sucesso!" };
    }
}
