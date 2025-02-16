namespace BackEnd.Domain.Entities
{
    public class Produto : Entity
    {
        public string Codigo { get; set; }
        public string Nome { get; set; }
        public int Quantidade { get; set; }
    }
}
