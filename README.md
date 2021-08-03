#Step 1: mvn clean package
#Step 2: docker-compose up -d
#Step 3: curl --location --request GET 'localhost:9999/products/1' \
--header 'Cookie: XSRF-TOKEN=df60f771-4d21-4d5e-a83a-9b45c007f511' 