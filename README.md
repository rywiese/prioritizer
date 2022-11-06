# prioritizer
Prioritizer is a full-stack web-app to visualize and manage the priority of `Items` from different (and possibly nested)
`Categories`.

The underlying data structure is a tree of FIFO queues supporting the novel `promote` operation, whereby an element is
popped from a queue of a child node and appended to the queue of the parent node.

Less formally, this is basically a generalized March Madness bracket.

Right now, the `Categories` are budgets and the `Items` are products that fit within those budgets. However, once this
budgeting use case is MVPed, we hope to make the app more extensible by supporting generic `Items` and `Categories`
defined in plugins.

## Running locally

### Clone
Clone the repo with
```
git clone https://github.com/rywiese/prioritizer.git
```

> **_TIP:_**  If you are using IntelliJ, follow the instructions below to get set up.
> 
> Import the project to IntelliJ with `File > New > Project from Existing Sources` and select the `prioritizer` folder
> created by the `git clone`.
> 
> Select the `Import project from external model` bubble and choose the `Gradle` option in the dropdown.

### Infrastructure
Prioritizer requires a connection to a [Neo4J](https://neo4j.com/) database. You have a few options to set this up...

#### AuraDB
[AuraDB](https://neo4j.com/cloud/platform/aura-graph-database/) is Neo4J's cloud-hosted Neo4J service. This just means
that the Neo4J _company_ will generously run the Neo4J _application_ on one of their servers, and give you a URI to
connect with. If you'd rather run the Neo4J application on your own machine, see one of the other options below.

[This guide](https://neo4j.com/docs/aura/auradb/getting-started/create-database/) walks through the process of creating
a Neo4J instance. You will need to create an account. Choose the "empty dataset" option - we will add our own data soon.
Once complete, you will see a popup that says something like:

> **Credentials for Instance01**
> 
> Username: <neo4j_username>
> 
> Generated password: <neo4j_password>

Save these credentials - they will be used to configure the app in [the next section](#configure).

Once your instance is created, you can see it in [AuraDB console](https://console.neo4j.io/?product=aura-db#databases).
You can always return to this console to view your instance and browse the data in it.

> **_TIP:_** your instance will pause automatically after a few days of inactivity. You will need to return to this
> console to resume it before running Prioritizer.

The following uri found in the console will be used to configure the app in [the next section](#configure).

> Connection URI: <neo4j_uri>

Follow [these instructions](https://neo4j.com/docs/aura/auradb/importing/import-database/#_import_database) to import
the sample data `.dump` file located in this repo at
[src/jvmMain/resources/neo4j-sample-data.dump](src/jvmMain/resources/neo4j-sample-data.dump). You should do this now
after creating your instance so you have something to work this. But you can also do this at any
time later if you'd like a fresh start on your database. You can also follow
[these instructions](https://neo4j.com/docs/aura/auradb/managing-databases/backup-restore-export/) to download your own
`.dump` files to restore later.  

#### Neo4J Desktop
Running [Neo4J Desktop](https://neo4j.com/download/) has not been explored yet...

#### Docker Compose
Running Neo4J with [Docker Compose](https://docs.docker.com/compose/) has not been explored yet...

### Configure
Prioritizer is configured by the following environment variables. See
[the earlier section on configuring Neo4J](#infrastructure) to find these values. If you are using IntelliJ, sit tight.
You will add these variables to your run configuration in [the next section](#run).
```
export NEO4J_URI=<neo4j_uri>
export NEO4J_USERNAME=<neo4j_username>
export NEO4J_PASSWORD=<neo4j_password>
```

### Run
Once the above environments variables are set, you can run the app with
```
./gradlew run
```

> **_TIP:_**  If you are using IntelliJ, you can set this command and all environment variables in a
> [run configuration](https://www.jetbrains.com/help/idea/run-debug-gradle.html).

### Use
Once the app is running, navigate to `http://localhost:8080` in your browser to interact with the GUI. The backend REST
API is available on this endpoint as well, though it is not documented yet.
