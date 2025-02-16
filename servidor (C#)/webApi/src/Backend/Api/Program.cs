using Infrastructure.Data;
using BackEnd.Application.Services;
using BackEnd.Application.Services.Produto;
using Microsoft.EntityFrameworkCore;
using BackEnd.Infrastructure.Data.Repositories.Interface;
using BackEnd.Infrastructure.Data.Repositories;

var builder = WebApplication.CreateBuilder(args);

// Adiciona o contexto do banco de dados
var connectionString = builder.Configuration.GetConnectionString("DefaultConnection");
builder.Services.AddDbContext<AppDbContext>(options =>
    options.UseMySql(connectionString, ServerVersion.AutoDetect(connectionString))
);

builder.Services.AddControllers();

// Configuração do Swagger
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

builder.Services.AddScoped<IExemploService, ExemploService>();
builder.Services.AddScoped<IProdutoService, ProdutoService>();
builder.Services.AddScoped<IProdutoRepository, ProdutoRepository>();

var app = builder.Build();

// Configuração do pipeline
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

app.MapControllers();

app.Run();
