<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <title>Buscar Producto</title>
</head>
<body>

<div class="container">
    <div class="row justify-content-center mt-5">
        <div class="col-md-6">
            <div class="card">
                <div class="card-body">
                    <h1 class="card-title text-center mb-4">Buscar Producto (nombre)</h1>
                    <form action="BuscarProductoServlet" method="post">
                        <div class="form-group">
                            <label for="nombre">Nombre del Producto:</label>
                            <input type="text" class="form-control" id="nombre" name="nombre" required>
                        </div>
                        <button type="submit" class="btn mt-2 btn-primary btn-block">Buscar</button>
                        <a href="/mongoventa/" class="btn mt-2 btn-secondary">Volver al inicio</a>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
