package com.erube.iceberg;

import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;

public interface IcebergOptions extends PipelineOptions {
    @Description("The URI of the Apache Iceberg warehouse location")
    @Default.String("gs://tmp-erube-iceberg/polaris/")
    String getWarehouseLocation();

    void setWarehouseLocation(String value);

    @Description("The name of the Apache Iceberg catalog")
    @Default.String("polariscatalog")
    String getCatalogName();

    void setCatalogName(String value);

    @Description("The name of the table to write to")
    @Default.String("haagenbergpolaris.test")
    String getTableName();

    void setTableName(String value);
}
