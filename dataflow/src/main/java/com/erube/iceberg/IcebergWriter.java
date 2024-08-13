package com.erube.iceberg;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import org.apache.beam.sdk.schemas.Schema;
import org.apache.beam.sdk.transforms.JsonToRow;
import org.apache.beam.sdk.values.PCollectionRowTuple;
import org.apache.beam.sdk.managed.Managed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.PipelineOptionsFactory;

public final class IcebergWriter {
    private static final String CATALOG_TYPE = "rest";

    private static final Schema SCHEMA =
            Schema.builder().addStringField("name").addInt32Field("id").build();

    private static final List<String> TABLE_ROWS = Arrays.asList(
            "{\"id\":0, \"name\":\"Alice\"}",
            "{\"id\":1, \"name\":\"Bob\"}",
            "{\"id\":2, \"name\":\"Charles\"}"
    );
    // Default Logger of the class
    private static final Logger LOGGER = LoggerFactory.getLogger(IcebergWriter.class);

    // Private constructor to avoid 'Utility classes should not have a public or default constructor.'
    private IcebergWriter() {
        // not called
    }

    public static void main(String[] args) {

        // Create the pipeline.
        PipelineOptionsFactory.register(IcebergOptions.class);
        IcebergOptions options = PipelineOptionsFactory.fromArgs(args).withValidation().as(IcebergOptions.class);
        Pipeline pipeline = Pipeline.create(options);

        // Configure the Iceberg source I/O
        Map<String, Object> catalogConfig = ImmutableMap.<String, Object>builder()
                .put("catalog_name", options.getCatalogName())
                .put("warehouse", options.getWarehouseLocation())
                .put("catalog_type", CATALOG_TYPE)
                .put("uri", "http://localhost:8181/")
                .put("scope", "PRINCIPAL_ROLE:ALL")
                .put("credential", "f4563fdc06cb3670:b2b29869e9106f7498ffc5d6055c295b")
                .build();

        ImmutableMap<String, Object> config = ImmutableMap.<String, Object>builder()
                .put("table", options.getTableName())
                .put("catalog_config", catalogConfig)
                .build();

        // Build the pipeline.
        var input = pipeline
                .apply(Create.of(TABLE_ROWS))
                .apply(JsonToRow.withSchema(SCHEMA));

        PCollectionRowTuple.of("input", input).apply(
                Managed.write(Managed.ICEBERG)
                        .withConfig(config)
        );

        pipeline.run();
    }
}
