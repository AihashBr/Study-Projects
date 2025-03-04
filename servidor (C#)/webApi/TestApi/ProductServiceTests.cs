using Application.DTOs.Product;
using Application.Services;
using AutoMapper;
using Domain.Model;
using Infrastructure.Data;
using Infrastructure.Repository.Interface;
using Microsoft.EntityFrameworkCore;
using Moq;
using System.ComponentModel.DataAnnotations;

namespace Tests;
public class ProductServiceTests
{
    private readonly Mock<IProductRepository> _productRepositoryMock;
    private readonly Mock<IMapper> _mapperMock;
    private readonly ProductService _productService;
    private readonly ApplicationDbContext _context;

    public ProductServiceTests()
    {
        var options = new DbContextOptionsBuilder<ApplicationDbContext>()
            .UseInMemoryDatabase(databaseName: "TestDb")
            .Options;

        _context = new ApplicationDbContext(options);
        _productRepositoryMock = new Mock<IProductRepository>();
        _mapperMock = new Mock<IMapper>();

        _productService = new ProductService(_productRepositoryMock.Object, _mapperMock.Object, _context);
    }

    [Fact]
    public void Validate_ProductCreateDTO_WithValidFields_ReturnTrue()
    {
        // Arrange
        var productCreateDTO = new ProductCreateDTO
        {
            Id = 0,
            Name = new string('A', 255),
            Amount = 0,
            Price = 0
        };

        // Act
        bool isValid = IsValidProductCreateDTO(productCreateDTO);

        // Assert
        Assert.True(isValid);
    }

    [Fact]
    public void Validate_ProductCreateDTO_WithNullFields_ReturnsErrors()
    {
        // Arrange
        var productCreateDTO = new ProductCreateDTO
        {
            Name = "",
            Amount = null,
            Price = null
        };

        // Act
        var validationResults = ValidationProductCreateDTO(productCreateDTO);

        // Assert
        Assert.Contains(validationResults, v => v.ErrorMessage == "Nome é obrigatório.");
        Assert.Contains(validationResults, v => v.ErrorMessage == "Quantidade é obrigatório.");
        Assert.Contains(validationResults, v => v.ErrorMessage == "Preço é obrigatório.");
    }

    [Fact]
    public void Validate_ProductCreateDTO_WithLongFields_ReturnError()
    {
        // Arrange
        var productCreateDTO = new ProductCreateDTO
        {
            Name = new string('A', 256),
            Amount = 0,
            Price = 0
        };

        // Act
        bool isValid = IsValidProductCreateDTO(productCreateDTO);

        // Assert
        Assert.False(isValid);
    }

    [Fact]
    public void Validate_CreateProduct_WithValidFields_ReturnSucces()
    {
        // Arrange
        var producCreatetDto = new ProductCreateDTO { Id = 1, Name = new string('A', 255), Amount = 10, Price = 20 };
        var product = new Product { Id = 1, Name = new string('A', 255), Amount = 10, Price = 20 };

        _mapperMock.Setup(m => m.Map<Product>(It.IsAny<ProductCreateDTO>())).Returns(product);
        _productRepositoryMock.Setup(r => r.AddProductAsync(It.IsAny<Product>())).Returns(Task.CompletedTask);

        // Act
        var result = _productService.CreateProduct(producCreatetDto);

        // Assert
        Assert.True(result.Status);
        Assert.Equal("Produto criado com sucesso!", result.Message);
    }

    [Fact]
    public void Validate_GetProductById_WithValidFields_ReturnSucces()
    {
        // Arrange
        var productViewDto = new ProductViewDTO { Id = 1, Name = new string('A', 255), Amount = 10, Price = 20 };
        var product = new Product { Id = 1, Name = new string('A', 255), Amount = 10, Price = 20 };

        _mapperMock.Setup(m => m.Map<ProductViewDTO>(It.IsAny<Product>())).Returns(productViewDto);
        _productRepositoryMock.Setup(r => r.GetProductByIdAsync(1)).ReturnsAsync(product);

        // Act
        var result = _productService.GetProductById(productViewDto.Id);

        // Assert
        Assert.True(result.Status);
        Assert.Equal("Produto com ID 1 encontrado com sucesso!", result.Message);
    }

    [Fact]
    public void Validate_UpdateProduct_WithValidFields_ReturnSucces()
    {
        // Arrange
        var producCreatetDto = new ProductCreateDTO { Id = 1, Name = new string('A', 255), Amount = 10, Price = 20 };
        var product = new Product { Id = 1, Name = new string('A', 255), Amount = 10, Price = 20 };

        _mapperMock.Setup(m => m.Map<Product>(It.IsAny<ProductCreateDTO>())).Returns(product);
        _productRepositoryMock.Setup(r => r.GetProductByIdAsync(1)).ReturnsAsync(product);
        _productRepositoryMock.Setup(r => r.UpdateProductAsync(It.IsAny<Product>())).Returns(Task.CompletedTask);

        // Act
        var result = _productService.UpdateProduct(producCreatetDto);

        // Assert
        Assert.True(result.Status);
        Assert.Equal("Produto atualizado com sucesso!", result.Message);
    }

    [Fact]
    public void Validate_DeleteProduct_WithValidFields_ReturnSucces()
    {
        // Arrange
        var productViewDto = new ProductViewDTO { Id = 1 };
        var product = new Product { Id = 1, Name = new string('A', 255), Amount = 10, Price = 20 };

        _productRepositoryMock.Setup(r => r.GetProductByIdAsync(1)).ReturnsAsync(product);
        _productRepositoryMock.Setup(r => r.DeleteProductAsync(It.IsAny<int>())).Returns(Task.CompletedTask);

        // Act
        var result = _productService.DeleteProduct(productViewDto.Id);

        // Assert
        Assert.True(result.Status);
        Assert.Equal("Produto com ID 1 deletado com sucesso!", result.Message);
    }

    public bool IsValidProductCreateDTO(ProductCreateDTO productCreateDTO)
    {
        var validationResults = new List<ValidationResult>();
        var isValid = Validator.TryValidateObject(productCreateDTO, new ValidationContext(productCreateDTO), validationResults, true);

        return isValid;
    }

    public List<ValidationResult> ValidationProductCreateDTO(ProductCreateDTO productCreateDTO)
    {
        var validationResults = new List<ValidationResult>();
        var isValid = Validator.TryValidateObject(productCreateDTO, new ValidationContext(productCreateDTO), validationResults, true);

        return validationResults;
    }
}
