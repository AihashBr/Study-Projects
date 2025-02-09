using Microsoft.AspNetCore.Mvc;
using MySql.Data.MySqlClient;

namespace MeuServidor.Controllers
{
    [ApiController]
    [Route("api/home/")]
    public class HomeController : ControllerBase
    {
        private readonly string _connectionString = "Server=108.181.92.70; Port=3306; database=servidor; UID=leandro; password=teste1234";

        [HttpGet("readDB")]
        public IActionResult readDB()
        {
            try
            {
                var tasksList = new List<object>();
                using (var conexao = new MySqlConnection(_connectionString))
                {
                    conexao.Open();
                    string sql = "SELECT id, nome, ean, quantidade, valor FROM estoque";
                    using (var comando = new MySqlCommand(sql, conexao))
                    {
                        using (var leitor = comando.ExecuteReader())
                        {
                            while (leitor.Read())
                            {
                                tasksList.Add(new
                                {
                                    id = leitor["id"],
                                    nome = leitor["nome"],
                                    ean = leitor["ean"],
                                    quantidade = leitor["quantidade"],
                                    valor = leitor["valor"]
                                });
                            }
                        }
                    }
                }
                return Ok(new { dbBase = tasksList });
            }
            catch (Exception er)
            {
                return StatusCode(500, new { error = "Erro no servidor."});
            }
        }

        [HttpPost("createDB")]
        public IActionResult createDB([FromBody] ConteudoCreateDB data)
        {
            try
            {
                using (var conexao = new MySqlConnection(_connectionString))
                {
                    conexao.Open();
                    string sql = "INSERT INTO estoque (nome, ean, quantidade) VALUES (@nome, @ean, @quantidade)";
                    using (var comando = new MySqlCommand(sql, conexao))
                    {
                        comando.Parameters.AddWithValue("@nome", data.nome);
                        comando.Parameters.AddWithValue("@ean", data.ean);
                        comando.Parameters.AddWithValue("@quantidade", data.quantidade);
                        int linhasAfetadas = comando.ExecuteNonQuery();
                        if (linhasAfetadas > 0)
                        {
                            return Ok();
                        }
                        else
                        {
                            return StatusCode(500, new { error = "Erro ao inserir os dados no banco." });
                        }
                    }
                }
            }
            catch (Exception er)
            {
                return StatusCode(500, new { error = "Erro no servidor."});
            }
        }

        [HttpPost("updateDB")]
        public IActionResult updateDB([FromBody] ConteudoUpdadeDB data)
        {
            try
            {
                using (var conexao = new MySqlConnection(_connectionString))
                {
                    conexao.Open();
                    string sql = "UPDATE estoque SET nome = @nome, ean = @ean, quantidade = @quantidade, valor = @valor WHERE id = @id";
                    using (var comando = new MySqlCommand(sql, conexao))
                    {
                        comando.Parameters.AddWithValue("@nome", data.nome);
                        comando.Parameters.AddWithValue("@ean", data.ean);
                        comando.Parameters.AddWithValue("@quantidade", data.quantidade);
                        comando.Parameters.AddWithValue("@valor", data.valor);
                        comando.Parameters.AddWithValue("@id", data.id);
                        comando.ExecuteNonQuery();
                    }
                }
                return Ok();
            }
            catch (Exception er)
            {
                return StatusCode(500, new { error = "Erro ao inserir os dados no banco." });
            }
        }

        [HttpPost("deleteDB")]
        public IActionResult deleteDB([FromBody] ConteudoUpdadeDB data)
        {
            try
            {
                using (var conexao = new MySqlConnection(_connectionString))
                {
                    conexao.Open();
                    string sql = "DELETE FROM estoque WHERE id = @id";
                    using (var comando = new MySqlCommand(sql, conexao))
                    {
                        comando.Parameters.AddWithValue("@id", data.id);
                        comando.ExecuteNonQuery();
                    }
                }
                return Ok();
            }
            catch (Exception er)
            {
                return StatusCode(500, new { error = "Erro ao inserir os dados no banco." });
            }
        }

        public class ConteudoCreateDB
        {
            public string nome { get; set; }
            public long ean { get; set; }
            public int quantidade { get; set; }
        }
        public class ConteudoUpdadeDB
        {
            public int id { get; set; }
            public string nome { get; set; }
            public long ean { get; set; }
            public int quantidade { get; set; }
            public int valor { get; set; }
        }
    }
}
