package com.etl.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        System.out.println("Windows Service started. Scheduling tasks every 10 minutes.");

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        //sqls 
         


        Runnable task = () -> {
 
           

            System.out.println("Executing scheduled task at " + java.time.LocalDateTime.now());
            // Add your business logic here
            performScheduledTask();
        };

        // Schedule the task to run every 10 minutes, starting immediately
        scheduler.scheduleAtFixedRate(task, 0, 10, TimeUnit.MINUTES);

        // Add shutdown hook to gracefully stop the scheduler
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down service...");
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
            }
            System.out.println("Service stopped.");
        }));

        // Keep the main thread alive
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void performScheduledTask() {
        // Implement your specific functionality here
        // For example: data processing, file operations, API calls, etc.
        System.out.println("Performing scheduled task...");

        // First database connection details - replace with your actual values
        String url1 = "jdbc:postgresql://192.168.0.250:5432/SiganeERP"; // Replace YOUR_DATABASE with your database name
        String user1 = "postgres"; // Replace with your PostgreSQL username
        String password1 = "superusuario"; // Replace with your PostgreSQL password

        // Second database connection details for upgrade - replace with your actual values
        String url2 = "jdbc:postgresql://localhost:5440/lookerstudio"; // Replace with your upgrade database name
        String user2 = "postgres"; // Replace with your PostgreSQL username for upgrade DB
        String password2 = "superusuario"; // Replace with your PostgreSQL password for upgrade DB
        

        String sqlComercialGeral= "SELECT\r\n" + //
                                "    -- Setor / Nota Fiscal\r\n" + //
                                "    sd.descricao                AS setor,\r\n" + //
                                "    nf.id_nota_fiscal,\r\n" + //
                                "    nf.nfnumero,\r\n" + //
                                "    nf.data_competencia,\r\n" + //
                                "    nf.id_participante,\r\n" + //
                                "    nf.id_vendedor,\r\n" + //
                                "    nf.id_modalidade_financeira,\r\n" + //
                                "    nf.id_empresa               AS empresa,\r\n" + //
                                "\r\n" + //
                                "    -- Vendedor / Supervisor\r\n" + //
                                "    v.apelido,\r\n" + //
                                "    v.id_supervisor,\r\n" + //
                                "    super.apelido               AS supervisor,\r\n" + //
                                "\r\n" + //
                                "    -- Empresa\r\n" + //
                                "    e.nome                      AS nome_empresa,\r\n" + //
                                "\r\n" + //
                                "    -- Cliente\r\n" + //
                                "    CASE\r\n" + //
                                "        WHEN (pes.abreviacao IS NULL OR LENGTH(TRIM(pes.abreviacao)) = 0)\r\n" + //
                                "        THEN pes.nome\r\n" + //
                                "        ELSE pes.abreviacao\r\n" + //
                                "    END                         AS abreviacao,\r\n" + //
                                "\r\n" + //
                                "    -- Endereço\r\n" + //
                                "    en.logradouro,\r\n" + //
                                "    en.numero,\r\n" + //
                                "    en.bairro,\r\n" + //
                                "    en.cep                      AS cep,\r\n" + //
                                "\r\n" + //
                                "    -- Cidade\r\n" + //
                                "    cid.id_cidade,\r\n" + //
                                "    cid.cid_nome,\r\n" + //
                                "    cid.cid_estado,\r\n" + //
                                "    cid.habitantes,\r\n" + //
                                "    cid.clientes_ativos,\r\n" + //
                                "    cid.cid_nome || ' hb.: ' || habitantes || ' cli ativos.: ' || cid.clientes_ativos AS cidade2,\r\n" + //
                                "\r\n" + //
                                "    -- Produto\r\n" + //
                                "    nfi.id_produto,\r\n" + //
                                "    pro.id_produto              AS cod_produto,\r\n" + //
                                "    pro.descricao               AS produto_nome,\r\n" + //
                                "    g.descricao                 AS grupo,\r\n" + //
                                "    l.descricao                 AS linha,\r\n" + //
                                "\r\n" + //
                                "    -- Tipo / CFOP\r\n" + //
                                "    mf.descricao                AS tipo_pagamento,\r\n" + //
                                "    CASE tipo_nota\r\n" + //
                                "        WHEN '0' THEN 'E'\r\n" + //
                                "        ELSE 'S'\r\n" + //
                                "    END                         AS tipo_es,\r\n" + //
                                "    nfi.cfop,\r\n" + //
                                "\r\n" + //
                                "    -- Valores agregados\r\n" + //
                                "    SUM(\r\n" + //
                                "        CASE\r\n" + //
                                "            WHEN nfi.cfop IN (1202, 1411, 2202) THEN nfi.valor_contabil * -1\r\n" + //
                                "            WHEN nfi.cfop IN (5101, 5102, 5403, 5405, 6101, 6102, 6401, 6402, 6403, 5401) THEN nfi.valor_contabil\r\n" + //
                                "            ELSE 0.0\r\n" + //
                                "        END\r\n" + //
                                "    )::NUMERIC(12, 2)           AS venda,\r\n" + //
                                "\r\n" + //
                                "    SUM(\r\n" + //
                                "        CASE\r\n" + //
                                "            WHEN nfi.cfop IN (5101, 5102, 5403, 5405, 6101, 6102, 6401, 6402, 6403, 5401)\r\n" + //
                                "            THEN nfi.valor_total - nfi.valor_liquido\r\n" + //
                                "            ELSE 0.0\r\n" + //
                                "        END\r\n" + //
                                "    )::NUMERIC(12, 2)           AS valor_rapel,\r\n" + //
                                "\r\n" + //
                                "    SUM(\r\n" + //
                                "        CASE\r\n" + //
                                "            WHEN nfi.cfop IN (5101, 5102, 5403, 5405, 6101, 6102, 6401, 6402, 6403, 5401)\r\n" + //
                                "                THEN quantidade * valor_custo_gerencial_medio\r\n" + //
                                "            WHEN nfi.cfop IN (1202, 1411, 2202)\r\n" + //
                                "                THEN (quantidade * valor_custo_gerencial_medio) * -1\r\n" + //
                                "            ELSE 0.0\r\n" + //
                                "        END\r\n" + //
                                "    )                           AS valor_custo,\r\n" + //
                                "\r\n" + //
                                "    COALESCE(SUM(\r\n" + //
                                "        CASE\r\n" + //
                                "            WHEN nfi.cfop IN (5101, 6101, 5102, 6102, 5401, 6401, 5405, 6405, 5403, 6403, 6108, 6107)\r\n" + //
                                "            THEN nfi.quantidade\r\n" + //
                                "            ELSE 0.0\r\n" + //
                                "        END\r\n" + //
                                "    ), 0.00)::NUMERIC(12, 2)    AS quantidade,\r\n" + //
                                "\r\n" + //
                                "    SUM(\r\n" + //
                                "        CASE\r\n" + //
                                "            WHEN nfi.cfop IN (5101, 6101, 5102, 6102, 5401, 6401, 5405, 6405, 5403, 6403, 6108, 6107)\r\n" + //
                                "            THEN nfi.peso_liquido\r\n" + //
                                "            ELSE 0.0\r\n" + //
                                "        END\r\n" + //
                                "    )                           AS peso_liquido,\r\n" + //
                                "\r\n" + //
                                "    SUM(nfi.valor_flex)         AS valor_flex,\r\n" + //
                                "\r\n" + //
                                "    SUM(\r\n" + //
                                "        CASE\r\n" + //
                                "            WHEN nfi.cfop IN (1202, 1411, 2202, 2411) THEN nfi.valor_total\r\n" + //
                                "            ELSE 0.0\r\n" + //
                                "        END\r\n" + //
                                "    )                           AS devolucao,\r\n" + //
                                "\r\n" + //
                                "    SUM(\r\n" + //
                                "        CASE\r\n" + //
                                "            WHEN nfi.cfop IN (5949, 6949) THEN nfi.valor_total\r\n" + //
                                "            ELSE 0.0\r\n" + //
                                "        END\r\n" + //
                                "    )                           AS troca,\r\n" + //
                                "\r\n" + //
                                "    SUM(\r\n" + //
                                "        CASE\r\n" + //
                                "            WHEN nfi.cfop IN (5910, 6910) THEN nfi.valor_total\r\n" + //
                                "            ELSE 0.0\r\n" + //
                                "        END\r\n" + //
                                "    )                           AS bonificacao,\r\n" + //
                                "\r\n" + //
                                "    0                           AS valor_comissao\r\n" + //
                                "\r\n" + //
                                "FROM notafiscal nf\r\n" + //
                                "\r\n" + //
                                "    -- Itens da nota\r\n" + //
                                "    INNER JOIN notafiscal_item       nfi   ON nf.id_nota_fiscal         = nfi.id_nota_fiscal\r\n" + //
                                "\r\n" + //
                                "    -- Vendedor e supervisor\r\n" + //
                                "    INNER JOIN vendedor              v     ON nf.id_vendedor             = v.id_interno\r\n" + //
                                "    LEFT  JOIN vendedor              super ON v.id_supervisor            = super.id_interno\r\n" + //
                                "\r\n" + //
                                "    -- Participante / cliente / endereço / cidade\r\n" + //
                                "    INNER JOIN pessoa                pes   ON nf.id_participante         = pes.id_pessoa\r\n" + //
                                "    INNER JOIN cliente               cli   ON pes.id_pessoa              = cli.id_pessoa\r\n" + //
                                "    INNER JOIN endereco              en    ON en.id_pessoa               = pes.id_pessoa\r\n" + //
                                "    INNER JOIN cidade                cid   ON cid.id_cidade              = en.id_cidade\r\n" + //
                                "\r\n" + //
                                "    -- Empresa\r\n" + //
                                "    INNER JOIN empresa               e     ON nf.id_empresa              = e.id_empresa\r\n" + //
                                "\r\n" + //
                                "    -- Produto / grupo / linha / setor\r\n" + //
                                "    INNER JOIN produto               pro   ON nfi.id_produto             = pro.id_produto\r\n" + //
                                "    LEFT  JOIN grupo                 g     ON pro.id_grupo               = g.codigo\r\n" + //
                                "    LEFT  JOIN linha                 l     ON pro.id_linha               = l.codigo\r\n" + //
                                "    LEFT  JOIN setor_vendas          sd    ON nf.id_setor_vendas         = sd.codigo\r\n" + //
                                "\r\n" + //
                                "    -- Modalidade financeira\r\n" + //
                                "    INNER JOIN modalidade_financeira mf    ON nf.id_modalidade_financeira = mf.id_modalidade_financeira\r\n" + //
                                "\r\n" + //
                                "WHERE\r\n" + //
                                "    nf.tipo                NOT IN ('VCD')\r\n" + //
                                "    AND nf.situacao         = '00'\r\n" + //
                                "    AND nf.id_carga        NOT IN (999999)\r\n" + //
                                "    AND nf.id_vendedor      > 0\r\n" + //
                                "    AND nf.id_empresa       = 1001\r\n" + //
                                "    AND nf.data_competencia BETWEEN '2026-03-01' AND CURRENT_DATE\r\n" + //
                                "    AND nfi.cfop            IN (\r\n" + //
                                "        1202, 1411, 1949, 2202, 2411,\r\n" + //
                                "        5101, 5102, 5403, 5405, 5401,\r\n" + //
                                "        5910, 5927, 5949,\r\n" + //
                                "        6101, 6102, 6108, 6401, 6403,\r\n" + //
                                "        6910, 6949\r\n" + //
                                "    )\r\n" + //
                                "\r\n" + //
                                "GROUP BY\r\n" + //
                                "    -- Nota fiscal\r\n" + //
                                "    nf.id_nota_fiscal, nf.nfnumero, nf.data_competencia,\r\n" + //
                                "    nf.id_participante, nf.id_vendedor,\r\n" + //
                                "    nf.id_modalidade_financeira,\r\n" + //
                                "\r\n" + //
                                "    -- Vendedor / supervisor\r\n" + //
                                "    v.apelido, v.id_supervisor,\r\n" + //
                                "    super.apelido,\r\n" + //
                                "\r\n" + //
                                "    -- Empresa\r\n" + //
                                "    e.nome,\r\n" + //
                                "\r\n" + //
                                "    -- Cliente / endereço\r\n" + //
                                "    pes.nome, pes.abreviacao,\r\n" + //
                                "    en.logradouro, en.numero, en.bairro, en.cep,\r\n" + //
                                "\r\n" + //
                                "    -- Cidade\r\n" + //
                                "    cid.id_cidade, cid.cid_nome, cid.cid_estado,\r\n" + //
                                "    cid.habitantes, cid.clientes_ativos,\r\n" + //
                                "\r\n" + //
                                "    -- Produto\r\n" + //
                                "    nfi.id_produto, pro.id_produto, pro.descricao,\r\n" + //
                                "    g.descricao, l.descricao,\r\n" + //
                                "\r\n" + //
                                "    -- Classificações\r\n" + //
                                "    sd.descricao, mf.descricao,\r\n" + //
                                "    tipo_es, nfi.cfop";

        List<NotaFiscalRow> resultado = new ArrayList<>();

        try (Connection conn1 = createPostgresConnection(url1, user1, password1)) {
            System.out.println("Connected to first PostgreSQL database successfully.");
            
            try (Statement stmt = conn1.createStatement();
                 ResultSet rs = stmt.executeQuery(sqlComercialGeral)) { // Replace your_table with actual table name
                while (rs.next()) {
                    NotaFiscalRow row = new NotaFiscalRow();

                    // Setor / Nota Fiscal
                    row.setSetor(rs.getString("setor"));
                    row.setIdNotaFiscal(rs.getInt("id_nota_fiscal"));
                    row.setNfNumero(rs.getString("nfnumero"));
                    row.setDataCompetencia(rs.getDate("data_competencia"));
                    row.setIdParticipante(rs.getInt("id_participante"));
                    row.setIdVendedor(rs.getInt("id_vendedor"));
                    row.setIdModalidadeFinanceira(rs.getInt("id_modalidade_financeira"));
                    row.setEmpresa(rs.getInt("empresa"));

                    // Vendedor / Supervisor
                    row.setApelido(rs.getString("apelido"));
                    row.setIdSupervisor(rs.getInt("id_supervisor"));
                    row.setSupervisor(rs.getString("supervisor"));

                    // Empresa
                    row.setNomeEmpresa(rs.getString("nome_empresa"));

                    // Cliente
                    row.setAbreviacao(rs.getString("abreviacao"));

                    // Endereço
                    row.setLogradouro(rs.getString("logradouro"));
                    row.setNumero(rs.getString("numero"));
                    row.setBairro(rs.getString("bairro"));
                    row.setCep(rs.getString("cep"));

                    // Cidade
                    row.setIdCidade(rs.getInt("id_cidade"));
                    row.setCidNome(rs.getString("cid_nome"));
                    row.setCidEstado(rs.getString("cid_estado"));
                    row.setHabitantes(rs.getInt("habitantes"));
                    row.setClientesAtivos(rs.getInt("clientes_ativos"));
                    row.setCidade2(rs.getString("cidade2"));

                    // Produto
                    row.setIdProduto(rs.getInt("id_produto"));
                    row.setCodProduto(rs.getInt("cod_produto"));
                    row.setProdutoNome(rs.getString("produto_nome"));
                    row.setGrupo(rs.getString("grupo"));
                    row.setLinha(rs.getString("linha"));

                    // Tipo / CFOP
                    row.setTipoPagamento(rs.getString("tipo_pagamento"));
                    row.setTipoEs(rs.getString("tipo_es"));
                    row.setCfop(rs.getInt("cfop"));

                    // Valores agregados
                    row.setVenda(rs.getBigDecimal("venda"));
                    row.setValorRapel(rs.getBigDecimal("valor_rapel"));
                    row.setValorCusto(rs.getBigDecimal("valor_custo"));
                    row.setQuantidade(rs.getBigDecimal("quantidade"));
                    row.setPesoLiquido(rs.getBigDecimal("peso_liquido"));
                    row.setValorFlex(rs.getBigDecimal("valor_flex"));
                    row.setDevolucao(rs.getBigDecimal("devolucao"));
                    row.setTroca(rs.getBigDecimal("troca"));
                    row.setBonificacao(rs.getBigDecimal("bonificacao"));
                    row.setValorComissao(rs.getBigDecimal("valor_comissao"));

                    resultado.add(row);
                    System.out.println("Total de linhas lidas: " + resultado.size());

                }
                
            }
           

        } catch (SQLException e) {
            System.err.println("First database operation failed: " + e.getMessage());
        }

        try (Connection conn2 = createPostgresConnection(url2, user2, password2)) {
            System.out.println("Connected to second PostgreSQL database (upgrade) successfully.");

            // SQL for checking if row exists
            String checkSql = "SELECT COUNT(*) FROM fato_comercial WHERE id_nota_fiscal = ? AND id_produto = ?";
            
            // SQL for UPDATE
            String updateSql = "UPDATE fato_comercial SET " +
                "setor = ?, nfnumero = ?, data_competencia = ?, id_participante = ?, id_vendedor = ?, " +
                "id_modalidade_financeira = ?, empresa = ?, apelido = ?, id_supervisor = ?, supervisor = ?, " +
                "nome_empresa = ?, abreviacao = ?, logradouro = ?, numero = ?, bairro = ?, cep = ?, " +
                "id_cidade = ?, cid_nome = ?, cid_estado = ?, habitantes = ?, clientes_ativos = ?, " +
                "cod_produto = ?, produto_nome = ?, grupo = ?, linha = ?, tipo_pagamento = ?, " +
                "tipo_es = ?, cfop = ?, venda = ?, valor_rapel = ?, valor_custo = ?, " +
                "quantidade = ?, peso_liquido = ?, valor_flex = ?, devolucao = ?, troca = ?, " +
                "bonificacao = ?, valor_comissao = ? " +
                "WHERE id_nota_fiscal = ? AND id_produto = ?";
            
            // SQL for INSERT
            String insertSql = "INSERT INTO fato_comercial (" +
                "setor, id_nota_fiscal, nfnumero, data_competencia, id_participante, id_vendedor, " +
                "id_modalidade_financeira, empresa, apelido, id_supervisor, supervisor, nome_empresa, " +
                "abreviacao, logradouro, numero, bairro, cep, id_cidade, cid_nome, cid_estado, " +
                "habitantes, clientes_ativos, id_produto, cod_produto, produto_nome, " +
                "grupo, linha, tipo_pagamento, tipo_es, cfop, venda, valor_rapel, valor_custo, " +
                "quantidade, peso_liquido, valor_flex, devolucao, troca, bonificacao, valor_comissao" +
                ") VALUES (" +
                "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?" +
                ")";

            int insertCount = 0, updateCount = 0;

            for (NotaFiscalRow row : resultado) {
                // Check if row exists
                boolean exists = false;
                try (PreparedStatement checkStmt = conn2.prepareStatement(checkSql)) {
                    checkStmt.setInt(1, row.getIdNotaFiscal());
                    checkStmt.setInt(2, row.getIdProduto());
                    try (ResultSet rs = checkStmt.executeQuery()) {
                        if (rs.next() && rs.getInt(1) > 0) {
                            exists = true;
                        }
                    }
                }

                if (exists) {
                    // UPDATE existing row
                    try (PreparedStatement updateStmt = conn2.prepareStatement(updateSql)) {
                        updateStmt.setString(1, row.getSetor());
                        updateStmt.setInt(2, Integer.parseInt(row.getNfNumero()));
                        updateStmt.setDate(3, row.getDataCompetencia());
                        updateStmt.setInt(4, row.getIdParticipante());
                        updateStmt.setInt(5, row.getIdVendedor());
                        updateStmt.setInt(6, row.getIdModalidadeFinanceira());
                        updateStmt.setInt(7, row.getEmpresa());
                        updateStmt.setString(8, row.getApelido());
                        updateStmt.setInt(9, row.getIdSupervisor());
                        updateStmt.setString(10, row.getSupervisor());
                        updateStmt.setString(11, row.getNomeEmpresa());
                        updateStmt.setString(12, row.getAbreviacao());
                        updateStmt.setString(13, row.getLogradouro());
                        updateStmt.setString(14, row.getNumero());
                        updateStmt.setString(15, row.getBairro());
                        updateStmt.setString(16, row.getCep());
                        updateStmt.setInt(17, row.getIdCidade());
                        updateStmt.setString(18, row.getCidNome());
                        updateStmt.setString(19, row.getCidEstado());
                        updateStmt.setInt(20, row.getHabitantes());
                        updateStmt.setInt(21, row.getClientesAtivos());
                        updateStmt.setInt(22, row.getCodProduto());
                        updateStmt.setString(23, row.getProdutoNome());
                        updateStmt.setString(24, row.getGrupo());
                        updateStmt.setString(25, row.getLinha());
                        updateStmt.setString(26, row.getTipoPagamento());
                        updateStmt.setString(27, row.getTipoEs());
                        updateStmt.setInt(28, row.getCfop());
                        updateStmt.setBigDecimal(29, row.getVenda());
                        updateStmt.setBigDecimal(30, row.getValorRapel());
                        updateStmt.setBigDecimal(31, row.getValorCusto());
                        updateStmt.setBigDecimal(32, row.getQuantidade());
                        updateStmt.setBigDecimal(33, row.getPesoLiquido());
                        updateStmt.setBigDecimal(34, row.getValorFlex());
                        updateStmt.setBigDecimal(35, row.getDevolucao());
                        updateStmt.setBigDecimal(36, row.getTroca());
                        updateStmt.setBigDecimal(37, row.getBonificacao());
                        updateStmt.setBigDecimal(38, row.getValorComissao());
                        updateStmt.setInt(39, row.getIdNotaFiscal());
                        updateStmt.setInt(40, row.getIdProduto());
                        
                        updateStmt.executeUpdate();
                        updateCount++;
                    }
                } else {
                    // INSERT new row
                    try (PreparedStatement insertStmt = conn2.prepareStatement(insertSql)) {
                        insertStmt.setString(1, row.getSetor());
                        insertStmt.setInt(2, row.getIdNotaFiscal());
                        insertStmt.setInt(3, Integer.parseInt(row.getNfNumero()));
                        insertStmt.setDate(4, row.getDataCompetencia());
                        insertStmt.setInt(5, row.getIdParticipante());
                        insertStmt.setInt(6, row.getIdVendedor());
                        insertStmt.setInt(7, row.getIdModalidadeFinanceira());
                        insertStmt.setInt(8, row.getEmpresa());
                        insertStmt.setString(9, row.getApelido());
                        insertStmt.setInt(10, row.getIdSupervisor());
                        insertStmt.setString(11, row.getSupervisor());
                        insertStmt.setString(12, row.getNomeEmpresa());
                        insertStmt.setString(13, row.getAbreviacao());
                        insertStmt.setString(14, row.getLogradouro());
                        insertStmt.setString(15, row.getNumero());
                        insertStmt.setString(16, row.getBairro());
                        insertStmt.setString(17, row.getCep());
                        insertStmt.setInt(18, row.getIdCidade());
                        insertStmt.setString(19, row.getCidNome());
                        insertStmt.setString(20, row.getCidEstado());
                        insertStmt.setInt(21, row.getHabitantes());
                        insertStmt.setInt(22, row.getClientesAtivos());
                        insertStmt.setInt(23, row.getIdProduto());
                        insertStmt.setInt(24, row.getCodProduto());
                        insertStmt.setString(25, row.getProdutoNome());
                        insertStmt.setString(26, row.getGrupo());
                        insertStmt.setString(27, row.getLinha());
                        insertStmt.setString(28, row.getTipoPagamento());
                        insertStmt.setString(29, row.getTipoEs());
                        insertStmt.setInt(30, row.getCfop());
                        insertStmt.setBigDecimal(31, row.getVenda());
                        insertStmt.setBigDecimal(32, row.getValorRapel());
                        insertStmt.setBigDecimal(33, row.getValorCusto());
                        insertStmt.setBigDecimal(34, row.getQuantidade());
                        insertStmt.setBigDecimal(35, row.getPesoLiquido());
                        insertStmt.setBigDecimal(36, row.getValorFlex());
                        insertStmt.setBigDecimal(37, row.getDevolucao());
                        insertStmt.setBigDecimal(38, row.getTroca());
                        insertStmt.setBigDecimal(39, row.getBonificacao());
                        insertStmt.setBigDecimal(40, row.getValorComissao());
                        
                        insertStmt.executeUpdate();
                        insertCount++;
                    }
                }
            }
            System.out.println("Successfully updated second database: " + insertCount + " rows inserted, " + updateCount + " rows updated.");

        } catch (SQLException e) {
            System.err.println("Second database operation failed: " + e.getMessage());
        }
    }

    private static Connection createPostgresConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
    
}