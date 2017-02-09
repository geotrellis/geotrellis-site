FROM nginx:1.10.3

COPY _site/ /var/www/

# Overwrite default nginx configuration
COPY nginx.conf /etc/nginx/

EXPOSE 8080
