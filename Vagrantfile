Vagrant::Config.run do |config|
  config.vm.box     = "precise64"
  config.vm.box_url = "http://files.vagrantup.com/precise64.box"

  # config.vm.network "33.33.33.10"
  config.vm.forward_port 27017, 27018

  config.vm.provision :puppet do |puppet|
    puppet.manifests_path = "vagrant/manifests"
    puppet.manifest_file  = "base.pp"
    puppet.module_path    = "vagrant/modules"
  end
end
