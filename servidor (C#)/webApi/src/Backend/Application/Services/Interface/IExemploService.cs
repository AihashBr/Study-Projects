namespace BackEnd.Application.Services
{
    public interface IExemploService
    {
        string GetMessage();
        string PostMessage(string mensagem);
    }
}
