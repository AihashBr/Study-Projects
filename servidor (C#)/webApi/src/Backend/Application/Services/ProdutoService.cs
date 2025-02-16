using BackEnd.Application.DTOs;
using BackEnd.Infrastructure.Data.Repositories.Interface;
using ProdutoEntity =  BackEnd.Domain.Entities.Produto;

namespace BackEnd.Application.Services.Produto
{
    public class ProdutoService : IProdutoService
    {
        private readonly IProdutoRepository _produtoRepository;

        public ProdutoService(IProdutoRepository produtoRepository)
        {
            _produtoRepository = produtoRepository;
        }

        public string CreateProduto(ProdutoDTO produtoDTO)
        {
            var produto = new ProdutoEntity
            {
                Nome = produtoDTO.Nome,
                Codigo = produtoDTO.Codigo,
                Quantidade = produtoDTO.Quantidade
            };

            _produtoRepository.AddProdutoAsync(produto).Wait();

            return $"Produto {produto.Nome} criado com sucesso!";
        }

        public string GetProdutoById(int id)
        {
            var produto = _produtoRepository.GetProdutoByIdAsync(id).Result;

            if (produto != null)
            {
                return $"Produto com ID {id} encontrado: {produto.Nome}, Código: {produto.Codigo}, Quantidade: {produto.Quantidade}";
            }

            return $"Produto com ID {id} não encontrado.";
        }
    }
}
