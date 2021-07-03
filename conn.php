
<?php
$servername="127.0.0.1";
//127.0.0.1:3307"
$username  ="root";
$password ="";
$dbname = "hbc_db"; 
$conn = mysqli_connect($servername, $username, $password, $dbname);

/*$cleardb_url = parse_url(getenv("CLEARDB_DATABASE_URL"));
$cleardb_server = $cleardb_url["host"];
$cleardb_username = $cleardb_url["user"];
$cleardb_password = $cleardb_url["pass"];
$cleardb_db = substr($cleardb_url["path"],1);
$active_group = 'default';
$query_builder = TRUE;
// Connect to DB
$conn = mysqli_connect($cleardb_server, $cleardb_username, $cleardb_password, $cleardb_db); */


if (!$conn)
{
        //die('Could not connect to MySQL35: ' . mysqli_error($conn));
        echo"connection not successful";
       //die('Could not connect to MySQL');
}else{
        //echo("DB connection success");
}
?>

