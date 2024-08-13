# Test Polaris with GCS

The purpose of this repository is to test Polaris Iceberg catalog on a GCP infra with : 
- [Cloud Dataflow (Apache Beam)](https://cloud.google.com/dataflow)
- [PyIceberg](https://pyiceberg.readthedocs.io/)

# Environment setup

To get started with Polaris, all the basic instructions are provided here : [QuickStart](https://polaris.io/#section/Quick-Start)

For the integration with GCS, you simply need to setup the environment variable : `GOOGLE_APPLICATION_CREDENTIALS` 

Assuming you started the service via docker, you can get the root credentials by running the following command : 
```
docker logs <your container name> | grep "root principal credentials" | cut -d ' ' -f 6-
```

Once you have the credentials, we can define a new catalog. (More details [here](https://polaris.io/#section/Quick-Start/Defining-a-Catalog))
To facilitate this process, you can import the postman collection, update the variable with the appropriate values and execute all requests according to their order. 

Once you created a new catalog, with its user etc.. you can start play with the Jupyter notebook and the Apache Beam job. 


> As of 08/13, the Apache Beam job is not working because the Iceberg connector doesn't support : uri, scope and credential
