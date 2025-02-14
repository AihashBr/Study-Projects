using Backend.Domain.Entities;
using Backend.Domain.Interfaces;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Backend.Application.Services
{
    public class ProdutoService
    {
        private readonly IProdutoRepository _produtoRepository;

        public ProdutoService(IProdutoRepository produtoRepository)
        {
            _produtoRepository = produtoRepository;
        }

        public async Task<IEnumerable<Produto>> GetAllProdutosAsync()
        {
            return await _produtoRepository.GetAllAsync();
        }

        public async Task<Produto> GetProdutoByIdAsync(int id)
        {
            return await _produtoRepository.GetByIdAsync(id);
        }

        public async Task AddProdutoAsync(Produto produto)
        {
            await _produtoRepository.AddAsync(produto);
        }

        public async Task UpdateProdutoAsync(Produto produto)
        {
            await _produtoRepository.UpdateAsync(produto);
        }

        public async Task DeleteProdutoAsync(int id)
        {
            await _produtoRepository.DeleteAsync(id);
        }
    }
}
