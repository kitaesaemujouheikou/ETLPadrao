package com.etl.service;

import java.math.BigDecimal;
import java.sql.Date;

public class NotaFiscalRow {

    // ─── Setor / Nota Fiscal ──────────────────────────────────────────────────
    String  setor;
    Integer idNotaFiscal;
    String  nfNumero;
    Date    dataCompetencia;
    Integer idParticipante;
    Integer idVendedor;
    Integer idModalidadeFinanceira;
    Integer empresa;

    // ─── Vendedor / Supervisor ────────────────────────────────────────────────
    String  apelido;
    Integer idSupervisor;
    String  supervisor;

    // ─── Empresa ──────────────────────────────────────────────────────────────
    String nomeEmpresa;

    // ─── Cliente ──────────────────────────────────────────────────────────────
    String abreviacao;

    // ─── Endereço ─────────────────────────────────────────────────────────────
    String logradouro;
    String numero;
    String bairro;
    String cep;

    // ─── Cidade ───────────────────────────────────────────────────────────────
    Integer idCidade;
    String  cidNome;
    String  cidEstado;
    Integer habitantes;
    Integer clientesAtivos;
    String  cidade2;

    // ─── Produto ──────────────────────────────────────────────────────────────
    Integer idProduto;
    Integer codProduto;
    String  produtoNome;
    String  grupo;
    String  linha;

    // ─── Tipo / CFOP ──────────────────────────────────────────────────────────
    String  tipoPagamento;
    String  tipoEs;
    Integer cfop;

    // ─── Valores Agregados ────────────────────────────────────────────────────
    BigDecimal venda;
    BigDecimal valorRapel;
    BigDecimal valorCusto;
    BigDecimal quantidade;
    BigDecimal pesoLiquido;
    BigDecimal valorFlex;
    BigDecimal devolucao;
    BigDecimal troca;
    BigDecimal bonificacao;
    BigDecimal valorComissao;

    // ─── Construtor vazio ─────────────────────────────────────────────────────
    public NotaFiscalRow() {}

    // ─── Construtor completo ──────────────────────────────────────────────────
    public NotaFiscalRow(
            String setor, Integer idNotaFiscal, String nfNumero, Date dataCompetencia,
            Integer idParticipante, Integer idVendedor, Integer idModalidadeFinanceira, Integer empresa,
            String apelido, Integer idSupervisor, String supervisor,
            String nomeEmpresa,
            String abreviacao,
            String logradouro, String numero, String bairro, String cep,
            Integer idCidade, String cidNome, String cidEstado, Integer habitantes, Integer clientesAtivos, String cidade2,
            Integer idProduto, Integer codProduto, String produtoNome, String grupo, String linha,
            String tipoPagamento, String tipoEs, Integer cfop,
            BigDecimal venda, BigDecimal valorRapel, BigDecimal valorCusto,
            BigDecimal quantidade, BigDecimal pesoLiquido, BigDecimal valorFlex,
            BigDecimal devolucao, BigDecimal troca, BigDecimal bonificacao, BigDecimal valorComissao
    ) {
        this.setor                  = setor;
        this.idNotaFiscal           = idNotaFiscal;
        this.nfNumero               = nfNumero;
        this.dataCompetencia        = dataCompetencia;
        this.idParticipante         = idParticipante;
        this.idVendedor             = idVendedor;
        this.idModalidadeFinanceira = idModalidadeFinanceira;
        this.empresa                = empresa;
        this.apelido                = apelido;
        this.idSupervisor           = idSupervisor;
        this.supervisor             = supervisor;
        this.nomeEmpresa            = nomeEmpresa;
        this.abreviacao             = abreviacao;
        this.logradouro             = logradouro;
        this.numero                 = numero;
        this.bairro                 = bairro;
        this.cep                    = cep;
        this.idCidade               = idCidade;
        this.cidNome                = cidNome;
        this.cidEstado              = cidEstado;
        this.habitantes             = habitantes;
        this.clientesAtivos         = clientesAtivos;
        this.cidade2                = cidade2;
        this.idProduto              = idProduto;
        this.codProduto             = codProduto;
        this.produtoNome            = produtoNome;
        this.grupo                  = grupo;
        this.linha                  = linha;
        this.tipoPagamento          = tipoPagamento;
        this.tipoEs                 = tipoEs;
        this.cfop                   = cfop;
        this.venda                  = venda;
        this.valorRapel             = valorRapel;
        this.valorCusto             = valorCusto;
        this.quantidade             = quantidade;
        this.pesoLiquido            = pesoLiquido;
        this.valorFlex              = valorFlex;
        this.devolucao              = devolucao;
        this.troca                  = troca;
        this.bonificacao            = bonificacao;
        this.valorComissao          = valorComissao;
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────

    public String getSetor() { return setor; }
    public void setSetor(String setor) { this.setor = setor; }

    public Integer getIdNotaFiscal() { return idNotaFiscal; }
    public void setIdNotaFiscal(Integer idNotaFiscal) { this.idNotaFiscal = idNotaFiscal; }

    public String getNfNumero() { return nfNumero; }
    public void setNfNumero(String nfNumero) { this.nfNumero = nfNumero; }

    public Date getDataCompetencia() { return dataCompetencia; }
    public void setDataCompetencia(Date dataCompetencia) { this.dataCompetencia = dataCompetencia; }

    public Integer getIdParticipante() { return idParticipante; }
    public void setIdParticipante(Integer idParticipante) { this.idParticipante = idParticipante; }

    public Integer getIdVendedor() { return idVendedor; }
    public void setIdVendedor(Integer idVendedor) { this.idVendedor = idVendedor; }

    public Integer getIdModalidadeFinanceira() { return idModalidadeFinanceira; }
    public void setIdModalidadeFinanceira(Integer idModalidadeFinanceira) { this.idModalidadeFinanceira = idModalidadeFinanceira; }

    public Integer getEmpresa() { return empresa; }
    public void setEmpresa(Integer empresa) { this.empresa = empresa; }

    public String getApelido() { return apelido; }
    public void setApelido(String apelido) { this.apelido = apelido; }

    public Integer getIdSupervisor() { return idSupervisor; }
    public void setIdSupervisor(Integer idSupervisor) { this.idSupervisor = idSupervisor; }

    public String getSupervisor() { return supervisor; }
    public void setSupervisor(String supervisor) { this.supervisor = supervisor; }

    public String getNomeEmpresa() { return nomeEmpresa; }
    public void setNomeEmpresa(String nomeEmpresa) { this.nomeEmpresa = nomeEmpresa; }

    public String getAbreviacao() { return abreviacao; }
    public void setAbreviacao(String abreviacao) { this.abreviacao = abreviacao; }

    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public Integer getIdCidade() { return idCidade; }
    public void setIdCidade(Integer idCidade) { this.idCidade = idCidade; }

    public String getCidNome() { return cidNome; }
    public void setCidNome(String cidNome) { this.cidNome = cidNome; }

    public String getCidEstado() { return cidEstado; }
    public void setCidEstado(String cidEstado) { this.cidEstado = cidEstado; }

    public Integer getHabitantes() { return habitantes; }
    public void setHabitantes(Integer habitantes) { this.habitantes = habitantes; }

    public Integer getClientesAtivos() { return clientesAtivos; }
    public void setClientesAtivos(Integer clientesAtivos) { this.clientesAtivos = clientesAtivos; }

    public String getCidade2() { return cidade2; }
    public void setCidade2(String cidade2) { this.cidade2 = cidade2; }

    public Integer getIdProduto() { return idProduto; }
    public void setIdProduto(Integer idProduto) { this.idProduto = idProduto; }

    public Integer getCodProduto() { return codProduto; }
    public void setCodProduto(Integer codProduto) { this.codProduto = codProduto; }

    public String getProdutoNome() { return produtoNome; }
    public void setProdutoNome(String produtoNome) { this.produtoNome = produtoNome; }

    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }

    public String getLinha() { return linha; }
    public void setLinha(String linha) { this.linha = linha; }

    public String getTipoPagamento() { return tipoPagamento; }
    public void setTipoPagamento(String tipoPagamento) { this.tipoPagamento = tipoPagamento; }

    public String getTipoEs() { return tipoEs; }
    public void setTipoEs(String tipoEs) { this.tipoEs = tipoEs; }

    public Integer getCfop() { return cfop; }
    public void setCfop(Integer cfop) { this.cfop = cfop; }

    public BigDecimal getVenda() { return venda; }
    public void setVenda(BigDecimal venda) { this.venda = venda; }

    public BigDecimal getValorRapel() { return valorRapel; }
    public void setValorRapel(BigDecimal valorRapel) { this.valorRapel = valorRapel; }

    public BigDecimal getValorCusto() { return valorCusto; }
    public void setValorCusto(BigDecimal valorCusto) { this.valorCusto = valorCusto; }

    public BigDecimal getQuantidade() { return quantidade; }
    public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }

    public BigDecimal getPesoLiquido() { return pesoLiquido; }
    public void setPesoLiquido(BigDecimal pesoLiquido) { this.pesoLiquido = pesoLiquido; }

    public BigDecimal getValorFlex() { return valorFlex; }
    public void setValorFlex(BigDecimal valorFlex) { this.valorFlex = valorFlex; }

    public BigDecimal getDevolucao() { return devolucao; }
    public void setDevolucao(BigDecimal devolucao) { this.devolucao = devolucao; }

    public BigDecimal getTroca() { return troca; }
    public void setTroca(BigDecimal troca) { this.troca = troca; }

    public BigDecimal getBonificacao() { return bonificacao; }
    public void setBonificacao(BigDecimal bonificacao) { this.bonificacao = bonificacao; }

    public BigDecimal getValorComissao() { return valorComissao; }
    public void setValorComissao(BigDecimal valorComissao) { this.valorComissao = valorComissao; }

    // ─── toString ─────────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "NotaFiscalRow{" +
                "idNotaFiscal="    + idNotaFiscal    +
                ", nfNumero='"     + nfNumero        + '\'' +
                ", dataCompetencia=" + dataCompetencia +
                ", vendedor='"     + apelido         + '\'' +
                ", produto='"      + produtoNome     + '\'' +
                ", venda="         + venda           +
                ", quantidade="    + quantidade      +
                '}';
    }
}
