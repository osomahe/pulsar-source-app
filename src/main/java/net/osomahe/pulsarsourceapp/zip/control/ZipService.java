package net.osomahe.pulsarsourceapp.zip.control;

import net.osomahe.pulsarsourceapp.message.boundary.MessageService;
import net.osomahe.pulsarsourceapp.zip.entity.ZipInfo;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@ApplicationScoped
public class ZipService {
    private static final Logger log = Logger.getLogger(MessageService.class);

    @Inject
    MessageService service;

    public void writeZipData(ZipInfo info) {
        byte[] result = Base64.getDecoder().decode(info.zipBase64);
        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(result))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                if (!zipEntry.isDirectory()) {
                    List<Byte> data = new ArrayList<>();
                    for (int c = zis.read(); c != -1; c = zis.read()) {
                        data.add((byte) c);
                    }
                    service.writeStringMessage(info.topic, new String(toArray(data), StandardCharsets.UTF_8), info.producer);
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        } catch (IOException e) {
            log.error("Cannot process Zip file: " + info, e);
        }
    }

    private byte[] toArray(List<Byte> list) {
        byte[] array = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }
}
