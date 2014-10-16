{
  "variables": {
    "name": "geotrellis-site",
    "version": "0.1.0",
    "aws_access_key": "{{env `AWS_ACCESS_KEY_ID`}}",
    "aws_secret_key": "{{env `AWS_SECRET_ACCESS_KEY`}}"
  },
  "builders": [
    {
      "type": "amazon-ebs",
      "access_key": "{{user `aws_access_key`}}",
      "secret_key": "{{user `aws_secret_key` }}",
      "region": "us-east-1",
      "source_ami": "ami-92e552fa",
      "instance_type": "m3.large",
      "security_group_id": "sg-f965ab9c",
      "ssh_username": "ubuntu",
      "ami_name": "{{user `name`}}-{{user `version`}}-{{timestamp}}",
      "tags": {
        "name": "{{user `name`}}",
        "version": "{{user `version`}}"
      }
    }
  ],
  "provisioners": [
    {
      "type": "shell",
      "inline": [
        "sudo apt-get update -qq",
        "sudo apt-get install python-pip python-dev -y",
        "sudo pip install ansible==1.7.1"
      ]
    },
    {
      "type": "ansible-local",
      "playbook_file": "/vagrant/build/site.yml",
      "playbook_dir": "/vagrant/build"
    }
  ]
}
