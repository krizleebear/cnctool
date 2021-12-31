package de.christianleberfinger.cnc.toollength;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TestFusionParser {

    Path dir = Paths.get("src/test/resources/");

    @Test
    void testParse() throws IOException {
        Path jsonFile = dir.resolve("tools-example.json");
        FusionToolParser.FusionTools tools = FusionToolParser.parse(jsonFile);

        assertNotNull(tools.tools);
        assertEquals(1, tools.tools.size());
        assertEquals(Optional.empty(), tools.get(123));
        Optional<FusionTool> optionalTool = tools.get(3);
        assertTrue(optionalTool.isPresent());

        FusionTool tool3 = optionalTool.get();
        System.out.println(tool3);

        assertEquals(3, tool3.getToolNumber());
        assertEquals(2.0, tool3.getDiameter(), 0.01);
        assertEquals(3.175, tool3.getShaftDiameter(), 0.01);
    }
}
