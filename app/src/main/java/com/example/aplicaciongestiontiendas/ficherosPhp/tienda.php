<?php
$server = "localhost";
$user = "root";
$pass = "clave";
$bd = "matfranvictor";

//Creamos la conexión
$conexion = mysqli_connect($server, $user, $pass,$bd)
or die("Ha sucedido un error inexperado en la conexion de la base de datos");

//generamos la consulta
$sql = "SELECT * FROM Tiendas";
echo $sql;
mysqli_set_charset($conexion, "utf8"); //formato de datos utf8

if(!$result = mysqli_query($conexion, $sql)) die();

$tienda = array(); //creamos un array

while($row = mysqli_fetch_array($result))
{
    $local=$row['Local'];
    $producto=$row['Producto'];
    $descripcion=$row['Descripcion'];
    $cantidad=$row['Cantidad'];
    $precio=$row['Precio'];
    $subtotal=$row['SubTotal'];
    
   


    $tienda[] = array('Local'=> $local,
                      'Producto'=> $producto,
                      'Descripcion'=> $descripcion,
                      'Cantidad'=> $cantidad,
                      'Precio'=> $precio,
                      'SubTotal'=> $subtotal);

}

//desconectamos la base de datos
$close = mysqli_close($conexion)
or die("Ha sucedido un error inexperado en la desconexion de la base de datos");


//Creamos el JSON
$json_string = json_encode($tienda);
echo $json_string;

//Si queremos crear un archivo json, sería de esta forma:
/*
$file = 'tienda.json';
file_put_contents($file, $json_string);
*/


?>