<?php
/*$use_name = "root";
$user_pass = "";
$host_name = "127.0.0.1";
$db_name = "hbc_db";

$con = mysqli_connect($host_name, $use_name, $user_pass, $db_name); */

include('connx.php');

if($conn){
  $image = $_POST["image"];
  $name = $_POST["name"];
  $url = $_POST["url"];


  //$name_url = "http://192.168.43.20:8081/hbc/images/".$name.".jpg";
  $name_url = $url.$name.".jpg";

  //$sql = "insert into imageinfo (name) values ('$name_url')";
  //$sql = "insert into patient (p_img) values ('$name_url') where (p_fullnames) == ('$name')";
  //$sql = "insert into patient (p_img) values ('$name_url')"; 
  $sql = "UPDATE patient SET p_img = '$name_url' WHERE p_fullnames = '$name'";
  
  $upload_path = "images/$name.jpg";

  if(mysqli_query($conn,$sql)){
    file_put_contents($upload_path, base64_decode($image));
    echo json_encode(array('response'=>'Image Uploaded Successfully'));
  }else{
    echo json_encode(array('response'=>'image upload failed'));
  }
}else{
  echo json_encode(array('response'=>'Image Upload Failed'));
}

mysqli_close($conn);

?> 