locals {
  services = [
    "user-service",
    "auth-service",
    "inventory-service",
    "order-service",
    "store-service",
    "shipping-service",
    "supplier-service",
    "billing-service",
    "review-service",
    "coupon-service",
    "gateway-service"
  ]
}

resource "aws_ecr_repository" "services" {
  for_each             = toset(local.services)
  name                 = "ecomarket/${each.value}"
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }
}
