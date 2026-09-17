package com.sneaksapp.knowledgebuilder.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.*;

@Slf4j
public abstract class JsonAliasMapper<T extends Enum<T>> {

    protected final Map<String, T> aliasMap =new HashMap<>();

    protected void loadAliases(
            String jsonPath,Class<T> enumClass) {

        try {

            ObjectMapper mapper =new ObjectMapper();

            InputStream inputStream =new ClassPathResource(
                            jsonPath
                    ).getInputStream();

            if (inputStream.available() == 0) {

                log.warn("Alias dosyası boş: {}",jsonPath);

                return;
            }

            Map<String, List<String>> aliases =
                    mapper.readValue(
                            inputStream,
                            new com.fasterxml.jackson.core.type.TypeReference<
                                    Map<String, List<String>>>() {
                            }
                    );

            for (Map.Entry<String, List<String>> entry
                    : aliases.entrySet()) {

                T value =Enum.valueOf(
                                enumClass,
                                entry.getKey()
                        );

                for (String alias: entry.getValue()) {

                    aliasMap.put(
                            alias.toLowerCase().trim(),
                            value);
                }
            }

            log.debug("Alias dosyası yüklendi: {}, aliasCount={}",jsonPath,aliasMap.size());

        } catch (Exception e) {

            log.error("Alias dosyası yüklenemedi: {}",jsonPath,e);

            throw new RuntimeException("Alias file could not be loaded: "+ jsonPath,e);
        }
    }

    public T map(String value,T unknownValue) {

        if (value == null|| value.isBlank()) {

            return unknownValue;
        }

        String normalized =value.toLowerCase().trim();

        // Birebir eşleşme
        T exact =aliasMap.get(normalized);

        if (exact != null) {
            return exact;
        }

        // Kelime bazlı eşleşme
        String[] tokens =normalized.split("[\\s,/_-]+");

        for (String token : tokens) {

            T mapped =aliasMap.get(token);

            if (mapped != null) {
                return mapped;
            }
        }

        return unknownValue;
    }

    protected List<T> mapList(
            List<String> values,
            T unknownValue) {

        if (values == null|| values.isEmpty()) {

            return Collections.singletonList(
                    unknownValue
            );
        }

        return values.stream()
                .map(value ->
                        map(value, unknownValue))
                .distinct()
                .toList();
    }
}