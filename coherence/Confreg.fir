;buildInfoPackage: chisel3, version: 3.3-SNAPSHOT, scalaVersion: 2.12.10, sbtVersion: 1.3.8
circuit Confreg : 
  module Confreg : 
    input clock : Clock
    input reset : UInt<1>
    input s_axi : {awaddr : UInt<64>, awprot : UInt<3>, awvalid : UInt<1>, flip awready : UInt<1>, wdata : UInt<64>, wstrb : UInt<8>, wvalid : UInt<1>, flip wready : UInt<1>, flip bresp : UInt<2>, flip bvalid : UInt<1>, bready : UInt<1>, araddr : UInt<64>, arprot : UInt<3>, arvalid : UInt<1>, flip arready : UInt<1>, flip rdata : UInt<64>, flip rresp : UInt<2>, flip rvalid : UInt<1>, rready : UInt<1>}
    output reg : {initPC : UInt<64>, resetReg : UInt<64>}
    
    reg initPC : UInt<64>, clock with : (reset => (reset, UInt<64>("h00"))) @[Confreg.scala 26:23]
    reg resetReg : UInt<64>, clock with : (reset => (reset, UInt<64>("h00"))) @[Confreg.scala 27:25]
    reg addr : UInt<64>, clock with : (reset => (reset, UInt<64>("h00"))) @[Confreg.scala 28:21]
    reg state : UInt<2>, clock with : (reset => (reset, UInt<1>("h00"))) @[Confreg.scala 29:22]
    reg.initPC <= initPC @[Confreg.scala 30:14]
    reg.resetReg <= resetReg @[Confreg.scala 31:16]
    s_axi.awready <= UInt<1>("h00") @[Confreg.scala 32:17]
    s_axi.arready <= UInt<1>("h00") @[Confreg.scala 33:17]
    s_axi.rvalid <= UInt<1>("h00") @[Confreg.scala 34:16]
    s_axi.wready <= UInt<1>("h00") @[Confreg.scala 35:16]
    s_axi.bvalid <= UInt<1>("h00") @[Confreg.scala 36:16]
    s_axi.rresp <= UInt<1>("h00") @[Confreg.scala 37:15]
    s_axi.bresp <= UInt<1>("h00") @[Confreg.scala 38:15]
    s_axi.rdata <= UInt<1>("h00") @[Confreg.scala 39:15]
    node _T = asUInt(UInt<1>("h00")) @[Conditional.scala 37:23]
    node _T_1 = asUInt(state) @[Conditional.scala 37:39]
    node _T_2 = eq(_T, _T_1) @[Conditional.scala 37:30]
    when _T_2 : @[Conditional.scala 40:58]
      s_axi.awready <= UInt<1>("h01") @[Confreg.scala 42:21]
      node _T_3 = eq(s_axi.awvalid, UInt<1>("h00")) @[Confreg.scala 43:24]
      s_axi.arready <= _T_3 @[Confreg.scala 43:21]
      node _T_4 = and(s_axi.awready, s_axi.awvalid) @[package.scala 330:43]
      when _T_4 : @[Confreg.scala 44:28]
        state <= UInt<1>("h01") @[Confreg.scala 45:15]
        addr <= s_axi.awaddr @[Confreg.scala 46:14]
        skip @[Confreg.scala 44:28]
      else : @[Confreg.scala 47:34]
        node _T_5 = and(s_axi.arready, s_axi.arvalid) @[package.scala 333:43]
        when _T_5 : @[Confreg.scala 47:34]
          state <= UInt<2>("h02") @[Confreg.scala 48:15]
          addr <= s_axi.awaddr @[Confreg.scala 49:14]
          skip @[Confreg.scala 47:34]
      skip @[Conditional.scala 40:58]
    else : @[Conditional.scala 39:67]
      node _T_6 = asUInt(UInt<2>("h02")) @[Conditional.scala 37:23]
      node _T_7 = asUInt(state) @[Conditional.scala 37:39]
      node _T_8 = eq(_T_6, _T_7) @[Conditional.scala 37:30]
      when _T_8 : @[Conditional.scala 39:67]
        s_axi.rvalid <= UInt<1>("h01") @[Confreg.scala 53:20]
        node _T_9 = eq(addr, UInt<32>("h080003000")) @[Confreg.scala 54:17]
        when _T_9 : @[Confreg.scala 54:36]
          s_axi.rresp <= UInt<1>("h00") @[Confreg.scala 55:21]
          s_axi.rdata <= initPC @[Confreg.scala 56:21]
          skip @[Confreg.scala 54:36]
        else : @[Confreg.scala 57:42]
          node _T_10 = eq(addr, UInt<32>("h080000000")) @[Confreg.scala 57:23]
          when _T_10 : @[Confreg.scala 57:42]
            s_axi.rresp <= UInt<1>("h00") @[Confreg.scala 58:21]
            s_axi.rdata <= resetReg @[Confreg.scala 59:21]
            skip @[Confreg.scala 57:42]
          else : @[Confreg.scala 60:19]
            s_axi.rresp <= UInt<1>("h00") @[Confreg.scala 61:21]
            s_axi.rdata <= UInt<64>("h0ffffffffffffffff") @[Confreg.scala 62:21]
            skip @[Confreg.scala 60:19]
        node _T_11 = and(s_axi.rready, s_axi.rvalid) @[package.scala 334:41]
        when _T_11 : @[Confreg.scala 64:27]
          state <= UInt<1>("h00") @[Confreg.scala 65:15]
          skip @[Confreg.scala 64:27]
        skip @[Conditional.scala 39:67]
      else : @[Conditional.scala 39:67]
        node _T_12 = asUInt(UInt<1>("h01")) @[Conditional.scala 37:23]
        node _T_13 = asUInt(state) @[Conditional.scala 37:39]
        node _T_14 = eq(_T_12, _T_13) @[Conditional.scala 37:30]
        when _T_14 : @[Conditional.scala 39:67]
          s_axi.wready <= UInt<1>("h01") @[Confreg.scala 69:20]
          node _T_15 = and(s_axi.wready, s_axi.wvalid) @[package.scala 331:41]
          when _T_15 : @[Confreg.scala 70:27]
            node _T_16 = eq(addr, UInt<32>("h080003000")) @[Confreg.scala 71:19]
            when _T_16 : @[Confreg.scala 71:38]
              initPC <= s_axi.wdata @[Confreg.scala 72:18]
              skip @[Confreg.scala 71:38]
            else : @[Confreg.scala 73:44]
              node _T_17 = eq(addr, UInt<32>("h080000000")) @[Confreg.scala 73:25]
              when _T_17 : @[Confreg.scala 73:44]
                resetReg <= s_axi.wdata @[Confreg.scala 74:20]
                skip @[Confreg.scala 73:44]
            state <= UInt<2>("h03") @[Confreg.scala 76:15]
            skip @[Confreg.scala 70:27]
          skip @[Conditional.scala 39:67]
        else : @[Conditional.scala 39:67]
          node _T_18 = asUInt(UInt<2>("h03")) @[Conditional.scala 37:23]
          node _T_19 = asUInt(state) @[Conditional.scala 37:39]
          node _T_20 = eq(_T_18, _T_19) @[Conditional.scala 37:30]
          when _T_20 : @[Conditional.scala 39:67]
            s_axi.bvalid <= UInt<1>("h01") @[Confreg.scala 80:20]
            s_axi.bresp <= UInt<1>("h00") @[Confreg.scala 81:19]
            node _T_21 = and(s_axi.bready, s_axi.bvalid) @[package.scala 332:41]
            when _T_21 : @[Confreg.scala 82:27]
              state <= UInt<1>("h00") @[Confreg.scala 83:15]
              skip @[Confreg.scala 82:27]
            skip @[Conditional.scala 39:67]
    
