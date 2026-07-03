resource "aws_db_subnet_group" "main" {
  name       = "ecomarket-db-subnet-group"
  subnet_ids = [aws_subnet.public_1.id, aws_subnet.public_2.id]

  tags = {
    Name = "ecomarket-db-subnet-group"
  }
}

resource "aws_db_instance" "mysql" {
  identifier           = "ecomarket-mysql"
  allocated_storage    = 20
  storage_type         = "gp2"
  engine               = "mysql"
  engine_version       = "8.0"
  instance_class       = "db.t3.micro"
  db_name              = "ecomarket"
  username             = "root"
  password             = "rootpassword123" # In a real environment, use Secrets Manager!
  parameter_group_name = "default.mysql8.0"
  skip_final_snapshot  = true
  publicly_accessible  = false

  vpc_security_group_ids = [aws_security_group.rds.id]
  db_subnet_group_name   = aws_db_subnet_group.main.name
}

output "rds_endpoint" {
  value = aws_db_instance.mysql.endpoint
}
