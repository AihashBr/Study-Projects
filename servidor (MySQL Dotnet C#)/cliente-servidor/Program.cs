var builder = WebApplication.CreateBuilder(args);
builder.Services.AddControllers();
var app = builder.Build();
app.UseStaticFiles();
app.MapGet("/", async context =>
{
    await context.Response.SendFileAsync("wwwroot/index.html");
});
app.MapControllers();
app.Run();