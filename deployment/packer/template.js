{
  "variables": {
    "version": "0.2.0"
  },
  "builders": [
    {
      "name": "geotrellis-site",
      "type": "amazon-ebs",
      "region": "us-east-1",
      "source_ami": "ami-fce3c696",
      "instance_type": "t2.medium",
      "security_group_id": "sg-f965ab9c",
      "ssh_username": "ubuntu",
      "ami_name": "geotrellis-site-{{timestamp}}-{{user `version`}}",
      "tags": {
        "Name": "geotrellis-site",
        "Version": "{{user `version`}}",
        "Created": "{{ isotime }}"
      },
      "associate_public_ip_address": true
    }
  ],
  "provisioners": [
    {
      "type": "shell",
      "inline": [
        "sleep 5",
        "sudo apt-get update -qq",
        "sudo apt-get install python-pip python-dev -y",
        "sudo pip install ansible==1.9.2"
      ]
    },
    {
      "type": "ansible-local",
      "playbook_file": "/opt/geotrellis-site/deployment/ansible/site.yml",
      "playbook_dir": "/opt/geotrellis-site/deployment/ansible",
      "inventory_file": "/opt/geotrellis-site/deployment/ansible/inventory/geotrellis-site"
    }
  ]
}
