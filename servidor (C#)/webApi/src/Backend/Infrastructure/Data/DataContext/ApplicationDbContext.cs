using Microsoft.EntityFrameworkCore;
using Domain.Model;
using Microsoft.Extensions.DependencyInjection;

namespace Infrastructure.Data;
public class ApplicationDbContext : DbContext
{
    public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options) : base(options) { }

    public DbSet<Product> Products { get; set; }
}