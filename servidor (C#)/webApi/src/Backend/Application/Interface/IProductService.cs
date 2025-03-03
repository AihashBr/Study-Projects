using Application.DTOs.Product;

namespace Application.Interface;
public interface IProductService
{
    string CreateProduct(ProductDTO productDTO);
    string GetProductById(int id);
    string UpdateProduct(ProductDTO productDTO);
    string DeleteProduct(int id);
}
