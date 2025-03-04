using Application.DTOs.Product;
using Domain.Model;
using Infrastructure.Data;
using Infrastructure.Repository;
using Microsoft.EntityFrameworkCore;
using Moq;
using System.ComponentModel.DataAnnotations;

namespace Tests;
public class ProductsTests
{
    private readonly Mock<ApplicationDbContext> _dbContextMock;
    private readonly ProductRepository _productRepository;

    public ProductsTests()
    {
        var options = new DbContextOptionsBuilder<ApplicationDbContext>()
            .UseInMemoryDatabase(databaseName: "TestDb")
            .Options;

        _dbContextMock = new Mock<ApplicationDbContext>(options);
        _productRepository = new ProductRepository(_dbContextMock.Object);
    }

    [Fact]
    public void Validate_ProductCreateDTO_WithNullFields_ReturnsValidationErrors()
    {
        // Arrange
        var productCreateDTO = new ProductCreateDTO
        {
            Name = "",
            Amount = null,
            Price = null
        };

        // Act
        var validationResults = new List<ValidationResult>();
        var isValid = Validator.TryValidateObject(productCreateDTO, new ValidationContext(productCreateDTO), validationResults, true);

        // Assert
        Assert.Equal("Quantidade é obrigatório.", validationResults[1].ErrorMessage);
        Assert.Equal("Preço é obrigatório.", validationResults[2].ErrorMessage);
        //Assert.Equal("Nome é obrigatório.", validationResults[3].ErrorMessage);
    }
}
