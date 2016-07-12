package com.tailoredshapes.clobber.security;

import com.google.inject.Key;
import com.google.inject.name.Names;
import com.tailoredshapes.clobber.GuiceTest;
import com.tailoredshapes.clobber.dao.DAO;
import com.tailoredshapes.clobber.model.Inventory;
import com.tailoredshapes.clobber.model.Metric;
import com.tailoredshapes.clobber.model.MetricType;
import com.tailoredshapes.clobber.model.User;
import com.tailoredshapes.clobber.model.builders.InventoryBuilder;
import com.tailoredshapes.clobber.model.builders.MetricBuilder;
import com.tailoredshapes.clobber.model.builders.MetricTypeBuilder;
import com.tailoredshapes.clobber.parsers.Parser;
import com.tailoredshapes.clobber.scopes.SimpleScope;
import com.tailoredshapes.clobber.serialisers.Serialiser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class InventoryParserTest {
    private SimpleScope scope;


    @Before
    public void init() {
        scope = GuiceTest.injector.getInstance(SimpleScope.class);
        scope.enter();
        scope.seed(Key.get(User.class, Names.named("current_user")), new User().setId(141211l));
    }

    @After
    public void tearDown() throws Exception {
        scope.exit();
    }

    @Test
    public void shouldParseASimpleInventory() throws Exception {
        Inventory inventory = new InventoryBuilder().build();
        scope.seed(Key.get(Inventory.class, Names.named("current_inventory")), inventory);

        Parser<Inventory> parser = GuiceTest.injector.getInstance(new Key<Parser<Inventory>>() {});
        Serialiser<Inventory, String> serialiser = GuiceTest.injector.getInstance(new Key<Serialiser<Inventory, String>>() {});

        Inventory inv = parser.parse(serialiser.serialise(inventory));
        assertEquals(inventory.getCategory().getFullname(), inv.getCategory().getFullname());
    }

    @Test
    public void shouldParseAnInventoryWithParent() throws Exception {
        DAO<Inventory> dao = GuiceTest.injector.getInstance(new Key<DAO<Inventory>>() {});

        Inventory parent = new InventoryBuilder().build();
        parent = dao.create(parent);

        Inventory inventory = new InventoryBuilder().parent(parent).build();

        scope.seed(Key.get(Inventory.class, Names.named("current_inventory")), inventory);

        Parser<Inventory> parser = GuiceTest.injector.getInstance(new Key<Parser<Inventory>>() {});
        Serialiser<Inventory, String> serialiser = GuiceTest.injector.getInstance(new Key<Serialiser<Inventory, String>>() {});

        Inventory inv = parser.parse(serialiser.serialise(inventory));

        assertEquals(parent, inv.getParent());

    }

    @Test
    public void shouldParseAnInventoryWithMetrics() throws Exception {
        List<Metric> metrics = new ArrayList<>(2);
        MetricType testType = new MetricTypeBuilder().build();
        Metric metric1 = new MetricBuilder().type(testType).value("Cassie").build();
        Metric metric2 = new MetricBuilder().type(testType).value("Archer").build();
        metrics.add(metric1);
        metrics.add(metric2);

        Inventory inventory = new InventoryBuilder().metrics(metrics).build();

        scope.seed(Key.get(Inventory.class, Names.named("current_inventory")), inventory);

        Parser<Inventory> parser = GuiceTest.injector.getInstance(new Key<Parser<Inventory>>() {});
        Serialiser<Inventory, String> serialiser = GuiceTest.injector.getInstance(new Key<Serialiser<Inventory, String>>() {});


        Inventory inv = parser.parse(new String(serialiser.serialise(inventory)));

        assertEquals(inv.getMetrics().get(0).getValue(), "Cassie");
        assertEquals(inv.getMetrics().get(0).getType().getName(), testType.getName());
        assertEquals(inv.getMetrics().get(1).getValue(), "Archer");
        assertEquals(inv.getMetrics().get(1).getType().getName(), testType.getName());
    }
}
