using System.ComponentModel.DataAnnotations;

namespace Application.DTOs.Product;
public class ProductCreateDTO
{
    public int Id { get; set; }

    [Required(ErrorMessage = "Nome é obrigatório.")]
    [StringLength(255, ErrorMessage = "Nome deve ter no máximo 255 caracteres.")]
    public string? Name { get; set; }

    [Required(ErrorMessage = "Quantidade é obrigatório.")]
    public decimal? Amount { get; set; }

    [Required(ErrorMessage = "Preço é obrigatório.")]
    public decimal? Price { get; set; }
}