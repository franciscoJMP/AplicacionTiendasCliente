<?php
$server = "localhost";
$user = "root";
$pass = "clave";
$bd = "matfranvictor";
$correo=$_GET['correo'];


//Creamos la conexión
$conexion = mysqli_connect($server, $user, $pass,$bd)
or die("Ha sucedido un error inexperado en la conexion de la base de datos");

//generamos la consulta
$sql = "SELECT * FROM Usuarios WHERE correo=$correo";
echo $sql;
mysqli_set_charset($conexion, "utf8"); //formato de datos utf8

if(!$result = mysqli_query($conexion, $sql)) die();

$usuarios = array(); //creamos un array

while($row = mysqli_fetch_array($result))
{
    $id=$row['idUsuario'];
    $correo=$row['correo'];
    $password=$row['password'];
    $nombre=$row['nombre'];
    $apellidos=$row['apellidos'];
    $tipo=$row['tipo'];
    $saldo=$row['saldo'];


    $usuarios[] = array('idUsuario'=> $id, 'correo'=> $correo, 'password'=> $password, 'nombre'=> $nombre,
                        'apellidos'=> $apellidos, 'tipo'=> $tipo, 'saldo'=> $saldo);

}

//desconectamos la base de datos
$close = mysqli_close($conexion)
or die("Ha sucedido un error inexperado en la desconexion de la base de datos");


//Creamos el JSON
$json_string = json_encode($usuarios);
echo $json_string;

//Si queremos crear un archivo json, sería de esta forma:
/*
$file = 'clientes.json';
file_put_contents($file, $json_string);
*/


?>