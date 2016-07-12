package com.tailoredshapes.clobber.modules;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.TypeLiteral;
import com.tailoredshapes.clobber.encoders.Encoder;
import com.tailoredshapes.clobber.encoders.SHAEncoder;
import com.tailoredshapes.clobber.model.*;

public class EncoderModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(new TypeLiteral<Encoder<User, ?>>() {
        })
                .to(new TypeLiteral<SHAEncoder<User>>() {
                });

        binder.bind(new TypeLiteral<Encoder<Inventory, ?>>() {
        })
                .to(new TypeLiteral<SHAEncoder<Inventory>>() {
                });

        binder.bind(new TypeLiteral<Encoder<Metric, ?>>() {
        })
                .to(new TypeLiteral<SHAEncoder<Metric>>() {
                });

        binder.bind(new TypeLiteral<Encoder<MetricType, ?>>() {
        })
                .to(new TypeLiteral<SHAEncoder<MetricType>>() {
                });

        binder.bind(new TypeLiteral<Encoder<Category, ?>>() {
        })
                .to(new TypeLiteral<SHAEncoder<Category>>() {
                });

    }
}
