using BackEnd.Application.DTOs;

namespace BackEnd.Application.Services.Produto
{
    public interface IProdutoService
    {
        string CreateProduto(ProdutoDTO produtoDTO);
        string GetProdutoById(int id);
    }
}
