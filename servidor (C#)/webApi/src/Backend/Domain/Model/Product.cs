using System.ComponentModel.DataAnnotations;

namespace Domain.Model;
public class Product
{
    [Key]
    public int Id { get; set; }

    [MaxLength(255)]
    public required string Name { get; set; }

    public decimal Amount { get; set; }

    public decimal Price { get; set; }
}