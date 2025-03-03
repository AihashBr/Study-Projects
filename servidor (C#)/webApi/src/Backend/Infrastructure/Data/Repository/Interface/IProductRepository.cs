using Domain.Model;

namespace Infrastructure.Repository.Interface;
public interface IProductRepository
{
    Task<Product> GetProductByIdAsync(int id);
    Task AddProductAsync(Product product);
    Task UpdateProductAsync(Product product);
    Task DeleteProductAsync(int id);
}
