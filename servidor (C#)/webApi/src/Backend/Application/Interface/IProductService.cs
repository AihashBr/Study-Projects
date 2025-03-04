using Application.DTOs.Product;
using Application.DTOs.Return;

namespace Application.Interface;
public interface IProductService
{
    ReturnStatus CreateProduct(ProductCreateDTO productDTO);
    ProductViewDTO GetProductById(int id);
    ReturnStatus UpdateProduct(ProductCreateDTO productDTO);
    ReturnStatus DeleteProduct(int id);
}
