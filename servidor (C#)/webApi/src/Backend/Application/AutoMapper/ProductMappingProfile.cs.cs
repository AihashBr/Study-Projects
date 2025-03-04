using Application.DTOs.Product;
using AutoMapper;
using Domain.Model;

namespace Application.AutoMapper;
public class MappingProfile : Profile
{
    public MappingProfile()
    {
        CreateMap<Product, ProductCreateDTO>().ReverseMap();
        CreateMap<ProductViewDTO, Product>().ReverseMap();
    }
}
