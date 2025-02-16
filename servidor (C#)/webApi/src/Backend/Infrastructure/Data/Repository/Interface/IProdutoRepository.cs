using BackEnd.Domain.Entities;

namespace BackEnd.Infrastructure.Data.Repositories.Interface
{
    public interface IProdutoRepository
    {
        Task<Produto> GetProdutoByIdAsync(int id);
        Task AddProdutoAsync(Produto produto);
    }
}
