using Application.DTOs.Return;

namespace Application.DTOs.Product;
public class ProductViewDTO : ReturnStatus
{
    public int Id { get; set; }
    public string? Name { get; set; }
    public decimal Amount { get; set; }
    public decimal Price { get; set; }
}